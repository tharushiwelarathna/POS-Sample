package lk.ijse.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.custom.CustomerBO;
import lk.ijse.pos.bo.custom.ItemBO;
import lk.ijse.pos.bo.custom.PurchaseOrderBO;
import lk.ijse.pos.db.DBConnection;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.dto.OrderDetailsDTO;
import lk.ijse.pos.dto.OrdersDTO;
import lk.ijse.pos.entity.Customer;
import lk.ijse.pos.entity.Item;
import lk.ijse.pos.entity.OrderDetails;
import lk.ijse.pos.entity.Orders;
import lk.ijse.pos.view.tblmodel.OrderDetailTM;


import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author : Sanu Vithanage
 * @since : 0.1.0
 **/

public class OrderFormController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbCustomerID;
    @FXML
    private JFXComboBox<String> cmbItemCode;


    @FXML
    private JFXTextField txtCustomerName;
    @FXML
    private JFXTextField txtDescription;
    @FXML
    private JFXTextField txtQtyOnHand;
    @FXML
    private JFXTextField txtUnitPrice;
    @FXML
    private JFXTextField txtQty;
    @FXML
    private TableView<OrderDetailTM> tblOrderDetails;

    private ObservableList<OrderDetailTM> olOrderDetails;

    private boolean update = false;
    @FXML
    private JFXButton btnRemove;
    @FXML
    private Label lblTotal;
    @FXML
    private JFXTextField txtOrderID;
    @FXML
    private JFXDatePicker txtOrderDate;

    private Connection connection;

//   private CustomerBO customerBO = new CustomerBOImpl();
//  private   ItemBO itemBO=new ItemBOImpl();
//    private PurchaseOrderBO purchaseOrderBO = new PurchaseOrderBOImpl();


    PurchaseOrderBO purchaseOrderBO= (PurchaseOrderBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.ORDER);


    CustomerBO customerBO= (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.CUSTOMER);

    ItemBO itemBO= (ItemBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.ITEM);


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
             connection = DBConnection.getInstance().getConnection();

            // Create a day cell factory
            Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            // Must call super
                            super.updateItem(item, empty);
                            LocalDate today = LocalDate.now();
                            setDisable(empty || item.compareTo(today) < 0);
                        }
                    };
                }
            };

            txtOrderDate.setDayCellFactory(dayCellFactory);
            loadAllData();
        } catch (SQLException ex) {
            Logger.getLogger(OrderFormController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            e.printStackTrace();
        }


        cmbCustomerID.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                String customerID = observable.getValue();
                if (customerID == null) {
                    txtCustomerName.setText("");
                    return;
                }

                try {
//                    CustomerDAO customerDAO = new CustomerDAOImpl();
                    CustomerDTO customer = customerBO.searchCustomer(customerID);

                    if (customer != null) {
                        txtCustomerName.setText(customer.getName());
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(OrderFormController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                String itemCode = observable.getValue();

                if (itemCode == null) {
                    txtDescription.setText("");
                    txtQtyOnHand.setText("");
                    txtUnitPrice.setText("");
                    txtQty.setText("");
                    return;
                }

                try {
//                    ItemDAOImpl itemDAO = new ItemDAOImpl();
                    ItemDTO item = itemBO.searchItem(itemCode);

                    if (item != null) {
                        String description = item.getDescription();
                        double unitPrice = item.getUnitPrice().doubleValue();
                        int qtyOnHand = item.getQtyOnHand();
                        txtDescription.setText(description);
                        txtUnitPrice.setText(unitPrice+"");
                        txtQtyOnHand.setText(qtyOnHand+"");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(OrderFormController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        tblOrderDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblOrderDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblOrderDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblOrderDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblOrderDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));

        olOrderDetails = FXCollections.observableArrayList();
        tblOrderDetails.setItems(olOrderDetails);

        tblOrderDetails.getItems().addListener(new ListChangeListener<OrderDetailTM>() {
            @Override
            public void onChanged(Change<? extends OrderDetailTM> c) {

                double total = 0.0;

                for (OrderDetailTM orderDetail : olOrderDetails) {
                    total += orderDetail.getTotal();
                }

                lblTotal.setText("Total : " + total);

            }
        });

        tblOrderDetails.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<OrderDetailTM>() {
            @Override
            public void changed(ObservableValue<? extends OrderDetailTM> observable, OrderDetailTM oldValue, OrderDetailTM newValue) {

                OrderDetailTM currentRow = observable.getValue();

                if (currentRow == null) {
                    cmbItemCode.getSelectionModel().clearSelection();
                    update = false;
                    btnRemove.setDisable(true);
                    return;
                }

                update = true;
                String itemCode = currentRow.getItemCode();
                btnRemove.setDisable(false);

                cmbItemCode.getSelectionModel().select(itemCode);
                txtQty.setText(currentRow.getQty() + "");

            }
        });

        btnRemove.setDisable(true);

    }

    private void loadAllData() throws SQLException {

//        CustomerDAO customerDAO = new CustomerDAOImpl();
        try {
            ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomers();
            cmbCustomerID.getItems().removeAll(cmbCustomerID.getItems());

            for (CustomerDTO customer: allCustomers){
                String id = customer.getcID();
                cmbCustomerID.getItems().add(id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

//        ItemDAO itemDAO = new ItemDAOImpl();
        try {
            ArrayList<ItemDTO> allItems = itemBO.getAllItems();
            cmbItemCode.getItems().removeAll(cmbItemCode.getItems());

            for (ItemDTO item: allItems){
                String itemCode = item.getCode();
                cmbItemCode.getItems().add(itemCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    private void navigateToMain(MouseEvent event) throws IOException {
        Label lblMainNav = (Label) event.getSource();
        Stage primaryStage = (Stage) lblMainNav.getScene().getWindow();

        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/pos/view/MainForm.fxml"));
        Scene mainScene = new Scene(root);
        primaryStage.setScene(mainScene);
        primaryStage.centerOnScreen();
    }

    @FXML
    private void btnSaveOnAction(ActionEvent event) {

        String itemCode = cmbItemCode.getSelectionModel().getSelectedItem();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());

        if (!update) {
            for (OrderDetailTM orderDetail : olOrderDetails) {
                if (orderDetail.getItemCode().equals(itemCode)) {
                    Alert error = new Alert(Alert.AlertType.ERROR, "Please update the item instead of adding", ButtonType.OK);
                    error.setHeaderText("Duplicate Entry Found");
                    error.setTitle("Duplicate Error");
                    error.show();
                    return;
                }
            }
        }

        OrderDetailTM orderDetail = new OrderDetailTM(
                itemCode,
                txtDescription.getText(),
                qty,
                unitPrice,
                qty * unitPrice);


        if (!update) {
            olOrderDetails.add(orderDetail);
            tblOrderDetails.setItems(olOrderDetails);
        } else {
            OrderDetailTM selectedRow = tblOrderDetails.getSelectionModel().getSelectedItem();
            int index = olOrderDetails.indexOf(selectedRow);
            olOrderDetails.set(index, orderDetail);
        }

        cmbItemCode.getSelectionModel().clearSelection();
        cmbItemCode.requestFocus();

    }

    @FXML
    private void btnRemoveOnAction(ActionEvent event) {
        OrderDetailTM selectedRow = tblOrderDetails.getSelectionModel().getSelectedItem();
        olOrderDetails.remove(selectedRow);

    }

    @FXML
    private void btnPlaceOrderOnAction(ActionEvent event) {

       // try {

            //connection.setAutoCommit(false);

            /*Add Order Record*/
//            OrderDAO orderDAO = new OrderDAOImpl();
//            Orders orders = new Orders(txtOrderID.getText(),parseDate(txtOrderDate.getEditor().getText()),cmbCustomerID.getSelectionModel().getSelectedItem());
//            boolean b1 = orderDAO.add(orders);
//            System.out.println("Order State :"+b1);
//            if (!b1) {
//                connection.rollback();
//                return;
//            }

            /*Add Order Details to the Table*/
//            OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();
//            for (OrderDetailTM orderDetailTM : olOrderDetails) {
//
//                OrderDetails orderDetails = new OrderDetails(
//                        txtOrderID.getText(),
//                        orderDetailTM.getItemCode(),
//                        orderDetailTM.getQty(),
//                        new BigDecimal(orderDetailTM.getUnitPrice()));
//
//                boolean b2 = orderDetailDAO.add(orderDetails);
//                System.out.println("Order Details State :"+b2);
//                if (!b2) {
//                    connection.rollback();
//                    return;
//                }

//                int qtyOnHand = 0;
////                ItemDAO itemDAO = new ItemDAOImpl();
//                Item item = itemBO.searchItem(orderDetailTM.getItemCode());
//
//                if (item!=null) {
//                    qtyOnHand = item.getQtyOnHand();
//                }
//
////                ItemDAO itemDAO = new ItemDAOImpl();
//                boolean b = itemBO.updateItemQtyOnHand(orderDetailTM.getItemCode(),qtyOnHand-orderDetailTM.getQty());
//                System.out.println("Item Qty Update State :"+b);
//                if (!b) {
//                    connection.rollback();
//                    return;
//                }
//
//            }

//            connection.commit();
//            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Order Placed", ButtonType.OK);
//            alert.show();
//
//        } catch (SQLException ex) {
//            try {
//                connection.rollback();
//            } catch (SQLException ex1) {
//                Logger.getLogger(OrderFormController.class.getName()).log(Level.SEVERE, null, ex1);
//            }
//            Logger.getLogger(OrderFormController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                connection.setAutoCommit(true);
//            } catch (SQLException ex) {
//                Logger.getLogger(OrderFormController.class.getName()).log(Level.SEVERE, null, ex);
//            }
       //}

        try {

            OrdersDTO orders = new OrdersDTO(txtOrderID.getText(), parseDate(txtOrderDate.getEditor().getText()), cmbCustomerID.getSelectionModel().getSelectedItem());

            ArrayList<OrderDetailsDTO> allOrderDetails = new ArrayList<>();

            for (OrderDetailTM orderDetailTM : olOrderDetails) {
                allOrderDetails.add(new OrderDetailsDTO(txtOrderID.getText(), orderDetailTM.getItemCode(), orderDetailTM.getQty(), new BigDecimal(orderDetailTM.getUnitPrice())));
            }
            // boolean b=orderBO.placeOrder(orders,allOrderDetails);
            if (purchaseOrderBO.purchaseOrder(orders)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Order Placed", ButtonType.OK);
                alert.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Date parseDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        try {
            return sdf.parse(date);
        } catch (ParseException ex) {

            Logger.getLogger(OrderFormController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
