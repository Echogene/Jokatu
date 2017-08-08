package jokatu.game.stage.machine;

import jokatu.game.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SingleStageMachine implements StageMachine {

	private final Stage<?> stage;

	public SingleStageMachine(@NotNull Stage<?> stage) {
		this.stage = stage;
	}

	@Nullable
	@Override
	public Stage<?> getCurrentStage() {
		return stage;
	}

	@NotNull
	@Override
	public Stage<?> advance() {
		return stage;
	}
}
