package com.example.auc88;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloController {

    // FXML components
    @FXML private ListView<AuctionItem> auctionList;
    @FXML private TextField searchField;
    @FXML private Label itemName;
    @FXML private Label itemDescription;
    @FXML private Label currentBidLabel;
    @FXML private Label timeLeftLabel;
    @FXML private TextField bidAmountField;
    @FXML private Label statusLabel;

    // Master list of all auctions (hard-coded demo data like eBay)
    private final ObservableList<AuctionItem> allAuctions = FXCollections.observableArrayList();
    private ObservableList<AuctionItem> displayedAuctions;

    // Inner model class for auction items (eBay-style)
    public static class AuctionItem {
        private String name;
        private String description;
        private double currentBid;
        private String timeLeft;

        public AuctionItem(String name, String description, double currentBid, String timeLeft) {
            this.name = name;
            this.description = description;
            this.currentBid = currentBid;
            this.timeLeft = timeLeft;
        }

        public String getName() { return name; }
        public String getDescription() { return description; }
        public double getCurrentBid() { return currentBid; }
        public void setCurrentBid(double currentBid) { this.currentBid = currentBid; }
        public String getTimeLeft() { return timeLeft; }

        @Override
        public String toString() {
            return name;
        }
    }

    @FXML
    public void initialize() {
        // Populate realistic eBay-style auction items
        allAuctions.addAll(
                new AuctionItem("Rolex Submariner Vintage Watch",
                        "1970s automatic diver's watch, excellent condition, original box & papers. 40mm case.",
                        2450.00, "2d 14h left"),
                new AuctionItem("MacBook Pro 16\" M4 Max",
                        "Brand new 2025 model, 64GB RAM, 2TB SSD, Space Black. Sealed retail box.",
                        3125.00, "5h 22m left"),
                new AuctionItem("Limited Edition Leica Q3 Camera",
                        "Digital compact camera with 28mm Summilux lens. Only 500 made worldwide.",
                        4850.00, "1d 9h left"),
                new AuctionItem("Tesla Model Y Long Range (2024)",
                        "Midnight Silver Metallic, Full Self-Driving capability, 19\" wheels. Low mileage.",
                        42800.00, "3d 7h left"),
                new AuctionItem("Rare Pokémon Charizard 1st Edition Shadowless",
                        "PSA 9 graded holographic card from Base Set. Museum-quality condition.",
                        875.00, "18h left")
        );

        displayedAuctions = FXCollections.observableArrayList(allAuctions);
        auctionList.setItems(displayedAuctions);

        // Custom cell factory to show eBay-like row info
        auctionList.setCellFactory(new Callback<>() {
            @Override
            public ListCell<AuctionItem> call(ListView<AuctionItem> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(AuctionItem item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.getName() + "   |   $" + String.format("%.2f", item.getCurrentBid()) +
                                    "   |   " + item.getTimeLeft());
                        }
                    }
                };
            }
        });

        // Show details when user selects an auction
        auctionList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newItem) -> {
            if (newItem != null) {
                showItemDetails(newItem);
            }
        });

        // Auto-select the first item on startup
        if (!displayedAuctions.isEmpty()) {
            auctionList.getSelectionModel().select(0);
        }

        statusLabel.setText("✅ Connected • 142 bidders online right now");
    }

    private void showItemDetails(AuctionItem item) {
        itemName.setText(item.getName());
        itemDescription.setText(item.getDescription());
        currentBidLabel.setText("$" + String.format("%.2f", item.getCurrentBid()));
        timeLeftLabel.setText(item.getTimeLeft());
        bidAmountField.clear();
        statusLabel.setText("✅ Connected • 142 bidders online right now");
    }

    @FXML
    protected void onPlaceBidClicked() {
        AuctionItem selected = auctionList.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No item selected", "Please select an auction to bid on.");
            return;
        }
        try {
            double newBid = Double.parseDouble(bidAmountField.getText().trim());
            if (newBid > selected.getCurrentBid()) {
                selected.setCurrentBid(newBid);
                currentBidLabel.setText("$" + String.format("%.2f", newBid));
                auctionList.refresh(); // update list row instantly
                statusLabel.setText("🎉 Bid accepted! You are now the highest bidder.");
            } else {
                showAlert("Bid too low", "Your bid must be higher than the current price.");
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid amount", "Please enter a valid dollar amount.");
        }
    }

    @FXML
    protected void onBuyItNowClicked() {
        AuctionItem selected = auctionList.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No item selected", "Please select an auction first.");
            return;
        }
        // Simulate Buy-It-Now (typical eBay feature)
        double buyNowPrice = selected.getCurrentBid() * 1.25; // 25% premium example
        selected.setCurrentBid(buyNowPrice);
        currentBidLabel.setText("$" + String.format("%.2f", buyNowPrice) + " (Buy It Now!)");
        auctionList.refresh();
        statusLabel.setText("🚀 Congratulations! Item purchased via Buy It Now.");
        showAlert("Purchase Complete", "You just bought the item for $" + String.format("%.2f", buyNowPrice) + "!");
    }

    @FXML
    protected void onSearchClicked() {
        String query = searchField.getText().trim().toLowerCase();
        displayedAuctions.clear();

        if (query.isEmpty()) {
            displayedAuctions.addAll(allAuctions);
        } else {
            for (AuctionItem item : allAuctions) {
                if (item.getName().toLowerCase().contains(query) ||
                        item.getDescription().toLowerCase().contains(query)) {
                    displayedAuctions.add(item);
                }
            }
        }

        auctionList.refresh();
        if (!displayedAuctions.isEmpty()) {
            auctionList.getSelectionModel().select(0);
        } else {
            statusLabel.setText("😕 No matching auctions found");
        }
    }

    @FXML
    protected void onPostItemClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Post a New Auction");
        alert.setHeaderText("Sell on Auction88");
        alert.setContentText("In the full eBay-style version this would open a rich form to upload photos, set starting price, duration, and Buy-It-Now option.\n\nDemo item would appear instantly in the list.");
        alert.showAndWait();

        // Demo: add a brand-new auction item after "posting"
        AuctionItem newItem = new AuctionItem("Your Item – Just Posted!",
                "This is a live example of a newly listed item. Start bidding now!",
                9.99, "7d left");
        allAuctions.add(0, newItem);
        displayedAuctions.add(0, newItem);
        auctionList.refresh();
        auctionList.getSelectionModel().select(0);
    }

    @FXML
    protected void onRefreshClicked() {
        // Simulate pulling latest bids from "server"
        statusLabel.setText("🔄 Refreshing live bids...");
        auctionList.refresh();
        // In a real app this would re-fetch data
        javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(1));
        pause.setOnFinished(e -> statusLabel.setText("✅ Refreshed • All bids up to date"));
        pause.play();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private String loggedInUser = "Guest";

    public void setLoggedInUser(String username) {
        this.loggedInUser = username;
        if (welcomeUserLabel != null) {
            welcomeUserLabel.setText("👤 " + username);
        }
    }

    @FXML
    protected void onLogoutClicked() {
        try {
            Stage stage = (Stage) welcomeUserLabel.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root, 420, 380));
            stage.setTitle("Auction88 - Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML private Label welcomeUserLabel;
}