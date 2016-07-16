package jokatu.game.stage.machine;

import jokatu.game.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SequentialStageMachine implements StageMachine {

	private int currentIndex = 0;
	private Stage<?> currentStage;
	private final List<StageFactory> factories;

	public SequentialStageMachine(@NotNull List<StageFactory> factories) {
		this.factories = factories;
	}

	@Nullable
	@Override
	public Stage<?> getCurrentStage() {
		return currentStage;
	}

	@NotNull
	@Override
	public Stage<?> advance() {
		StageFactory stageFactory = factories.get(currentIndex++);
		currentStage = stageFactory.produce();
		return currentStage;
	}
}
