// %1126721330229:hoplugins.transfers.ui.component%
package hoplugins.transfers.ui.component;

import hoplugins.transfers.constants.TransferTypes;

import javax.swing.JComboBox;


/**
 * ComboBox to edit the TrainingType
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class TransferTypeComboBox extends JComboBox {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TrainingComboBox object.
     */
    public TransferTypeComboBox() {
        super();

        for (int i = -1; i < TransferTypes.NUMBER; i++) {
            addItem(TransferTypes.getTransferDesc(i));
        }
    }
}
