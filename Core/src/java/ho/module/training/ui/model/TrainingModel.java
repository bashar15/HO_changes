package ho.module.training.ui.model;

import ho.core.model.player.Spieler;
import ho.module.training.OldTrainingManager;

import java.util.ArrayList;
import java.util.List;

public class TrainingModel {

	/** The currently selected player */
	private Spieler activePlayer;
	private OldTrainingManager skillupManager;
	private final List<ModelChangeListener> listeners = new ArrayList<ModelChangeListener>();

	public Spieler getActivePlayer() {
		return activePlayer;
	}

	public void setActivePlayer(Spieler player) {
		if ((this.activePlayer == null && player != null)
				|| (this.activePlayer != null && player == null)
				|| (this.activePlayer != null && player != null && this.activePlayer.getSpielerID() != player
						.getSpielerID())) {
			this.activePlayer = player;
			this.skillupManager = null;
			fireActivePlayerChanged();
		}
	}

	public OldTrainingManager getSkillupManager() {
		if (this.skillupManager == null) {
			this.skillupManager = new OldTrainingManager(this.activePlayer);
		}
		return this.skillupManager;
	}

	public void addModelChangeListener(ModelChangeListener listener) {
		if (!this.listeners.contains(listener)) {
			this.listeners.add(listener);
		}
	}

	public void removeModelChangeListener(ModelChangeListener listener) {
		this.listeners.remove(listener);
	}

	private void fireActivePlayerChanged() {
		for (int i = this.listeners.size() - 1; i >= 0; i--) {
			this.listeners.get(i).activePlayerChanged();
		}
	}
}
