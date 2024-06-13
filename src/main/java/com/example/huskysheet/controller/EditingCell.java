package com.example.huskysheet.controller;

import com.example.huskysheet.client.Utils.SpreadsheetSliceView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * Table cell which edits on losing focus.
 * All credit to JHeut at
 * <a href="https://stackoverflow.com/questions/29958905/javafx-editable-cell-with-focus-change-to-different-populated-cell">source</a>
 * for the majority of the implementation, I had struggled for 2 days to implement this
 * @author Jackson Magas
 */
class EditingCell extends TableCell<SpreadsheetSliceView, String> {
    private final TextField textField = new TextField();

    public EditingCell() {
        itemProperty().addListener((obx, oldItem, newItem) -> {
            if (newItem == null) {
                setText(null);
            } else {
                setText(newItem);
            }
        });

        setGraphic(textField);
        setContentDisplay(ContentDisplay.TEXT_ONLY);

        textField.setOnAction(evt -> {
            commitEdit(textField.getText());
        });
        textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                commitEdit(textField.getText());
            }
        });
        textField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case ESCAPE -> {
                    textField.setText(getItem());
                    cancelEdit();
                    event.consume();
                }
                case RIGHT -> {
                    getTableView().getSelectionModel().selectRightCell();
                    event.consume();
                }
                case LEFT -> {
                    getTableView().getSelectionModel().selectLeftCell();
                    event.consume();
                }
                case UP -> {
                    getTableView().getSelectionModel().selectAboveCell();
                    event.consume();
                }
                case DOWN -> {
                    getTableView().getSelectionModel().selectBelowCell();
                    event.consume();
                }
            }
        });
    }

    /**
     * Expose the underlying text field for adding event listeners.
     * @author Jackson Magas
     */
    public TextField getTextField() {
        return textField;
    }


    // set the text of the text field and display the graphic
    @Override
    public void startEdit() {
        super.startEdit();
        textField.setText(getItem());
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        textField.requestFocus();
        textField.selectPositionCaret(textField.getLength());
    }

    // revert to text display
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    // commits the edit. Update property if possible and revert to text display
    @Override
    public void commitEdit(String item) {
        // This block is necessary to support commit on losing focus, because the baked-in mechanism
        // sets our editing state to false before we can intercept the loss of focus.
        // The default commitEdit(...) method simply bails if we are not editing...
        if (! isEditing() && ! item.equals(getItem())) {
            var table = getTableView();
            if (table != null) {
                var column = getTableColumn();
                var event = new TableColumn.CellEditEvent<>(table,
                    new TablePosition<>(table, getIndex(), column),
                    TableColumn.editCommitEvent(), item);
                Event.fireEvent(column, event);
            }
        }
        textField.setText(item);

        super.commitEdit(item);

        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

}
