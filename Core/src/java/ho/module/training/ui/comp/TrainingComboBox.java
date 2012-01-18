// %1126721451244:hoplugins.trainingExperience.ui.component%
package ho.module.training.ui.comp;

import ho.module.training.Trainings;

import javax.swing.JComboBox;

import plugins.ITeam;

/**
 * ComboBox to edit the TrainingType
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 * Seb04 - Simplified and removed General and STamina training.
 */
public class TrainingComboBox extends JComboBox {
	private static final long serialVersionUID = 303608674207819922L;
	/**
     * Creates a new TrainingComboBox object.
     */
    public TrainingComboBox() {
        super();
        for (int i = ITeam.TA_STANDARD; i <= ITeam.TA_EXTERNALATTACK;  i++)
        {
        	addItem(new CBItem(Trainings.getTrainingDescription(i), i));
        }
    }
}
