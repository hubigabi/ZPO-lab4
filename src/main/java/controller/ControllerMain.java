package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ColorEnum;
import model.TableData;
import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class ControllerMain {

    @FXML
    private Button buttonCheck;

    @FXML
    private TextField textFieldSetValue;

    @FXML
    private TableView<TableData> tableView;

    @FXML
    private TableColumn<TableData, Integer> tableColumnNo;

    @FXML
    private TableColumn<TableData, String> tableColumnGetter;

    @FXML
    private TableColumn<TableData, String> tableColumnValue;

    @FXML
    private ComboBox<Method> comboBoxSetters;

    @FXML
    private TextField textFieldClassName;

    @FXML
    private ComboBox<Method> comboBoxInvoke;

    @FXML
    private Button buttonInvoke;

    @FXML
    private Button buttonSetValue;

    @FXML
    private Label labelTime;

    private ObservableList<TableData> tableData;
    private ObservableList<Method> setttersMethods;
    private ObservableList<Method> invokeMethods;
    private Object objectCreated;

    public void fillTableView(Method[] methods) {
        AtomicInteger runCount = new AtomicInteger(1);
        tableData.clear();
        Arrays.stream(methods)
                .filter(method -> method.getName().startsWith("get")
                        && method.getParameterCount() == 0)
                .forEach((method) -> {
                    method.setAccessible(true);
                    Object returnValue = null;
                    try {
                        returnValue = Optional.ofNullable(method.invoke(objectCreated))
                                .orElseGet(() -> "");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    tableData.add(new TableData(runCount.getAndIncrement(), method.getName(), returnValue.toString()));
                });
        tableView.setItems(tableData);
    }

    public void fillComboBoxSetters(Method[] methods) {
        setttersMethods.clear();
        Arrays.stream(methods)
                .filter(method -> method.getName().startsWith("set"))
                .filter(method -> Arrays.stream(
                        method.getParameterTypes())
                        .allMatch(parameter ->
                                ClassUtils.isPrimitiveOrWrapper(parameter) || parameter.equals(Date.class)
                                        || parameter.equals(Enum.class) || parameter.isEnum()))
                .forEach((method) -> {
                    method.setAccessible(true);
                    setttersMethods.add(method);
                });
        comboBoxSetters.setItems(setttersMethods);
    }

    public void fillComboBoxInvokeMethods(Method[] methods) {
        invokeMethods.clear();
        Arrays.stream(methods)
                .filter(method -> method.getParameterCount() == 0)
                .forEach((method) -> {
                    method.setAccessible(true);
                    invokeMethods.add(method);
                });
        comboBoxInvoke.setItems(invokeMethods);
    }

    public void addDataToTable() {
        tableData = FXCollections.observableArrayList();
        tableColumnNo.setCellValueFactory(new PropertyValueFactory<>("no"));
        tableColumnGetter.setCellValueFactory(new PropertyValueFactory<>("getterName"));
        tableColumnValue.setCellValueFactory(new PropertyValueFactory<>("getterValue"));
        tableView.setItems(tableData);
    }

    public void initialize() {
        setttersMethods = FXCollections.observableArrayList();
        invokeMethods = FXCollections.observableArrayList();
        addDataToTable();
    }

    @FXML
    void buttonCheckOnAction(ActionEvent event) {
        String className = textFieldClassName.getText();
        Class<?> classCreated = null;
        try {
            classCreated = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        long startTime = System.nanoTime();
        try {
            objectCreated = classCreated.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        long endTime = System.nanoTime();
        labelTime.setText("Time to create object:\n" + (endTime - startTime) / 1000 + "ms");

        Method[] methods = classCreated.getDeclaredMethods();
        fillTableView(methods);
        fillComboBoxSetters(methods);
        fillComboBoxInvokeMethods(methods);
    }

    @FXML
    void buttonInvokeMethodOnAction(ActionEvent event) {
        try {
            Method method = Class.forName(textFieldClassName.getText())
                    .getMethod(comboBoxInvoke.getSelectionModel().getSelectedItem().getName());

            long startTime = System.nanoTime();
            Object returnValue = method.invoke(objectCreated);
            long endTime = System.nanoTime();
            labelTime.setText("Time to invoke method:\n" + (endTime - startTime) / 1000 + "ms");
            System.out.println(returnValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void buttonSetValueOnAction(ActionEvent actionEvent) throws Exception {
        Method method = objectCreated.getClass().getDeclaredMethod(comboBoxSetters.getSelectionModel().getSelectedItem().getName(),
                comboBoxSetters.getSelectionModel().getSelectedItem().getParameterTypes());
        method.setAccessible(true);

        Integer parametersNumber = method.getParameterCount();
        Parameter[] parameters = method.getParameters();

        if (parametersNumber == 1) {
            Class<?> parameterType = parameters[0].getType();
            long startTime = System.nanoTime();

            if (parameterType.equals(int.class)) {
                method.invoke(objectCreated, Integer.parseInt(textFieldSetValue.getText()));
            } else if (parameterType.equals(byte.class)) {
                method.invoke(objectCreated, Byte.parseByte(textFieldSetValue.getText()));
            } else if (parameterType.equals(short.class)) {
                method.invoke(objectCreated, Short.parseShort(textFieldSetValue.getText()));
            } else if (parameterType.equals(long.class)) {
                method.invoke(objectCreated, Long.parseLong(textFieldSetValue.getText()));
            } else if (parameterType.equals(float.class)) {
                method.invoke(objectCreated, Float.parseFloat(textFieldSetValue.getText()));
            } else if (parameterType.equals(double.class)) {
                method.invoke(objectCreated, Double.parseDouble(textFieldSetValue.getText()));
            } else if (parameterType.equals(boolean.class)) {
                method.invoke(objectCreated, Boolean.parseBoolean(textFieldSetValue.getText()));
            } else if (parameterType.equals(char.class)) {
                method.invoke(objectCreated, textFieldSetValue.getText().charAt(0));
            } else if (parameterType.equals(String.class)) {
                method.invoke(objectCreated, textFieldSetValue.getText());
            } else if (parameterType.equals(Date.class)) {
                method.invoke(objectCreated, new Date(Long.parseLong(textFieldSetValue.getText())));
            } else if (parameterType.isEnum()) {
                method.invoke(objectCreated, Enum.valueOf((Class<Enum>) parameterType, textFieldSetValue.getText()));
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Wrong parameter");
                errorAlert.setContentText("The parameter is not primitive, String or Date!");
                errorAlert.showAndWait();
            }

            long endTime = System.nanoTime();
            labelTime.setText("Time to set a new value:\n" + (endTime - startTime) / 1000 + "ms");

            try {
                Class<?> classCreated = Class.forName(textFieldClassName.getText());
                Method[] methods = classCreated.getDeclaredMethods();
                tableData.clear();
                fillTableView(methods);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
