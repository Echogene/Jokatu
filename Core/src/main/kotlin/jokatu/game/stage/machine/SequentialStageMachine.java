package jokatu.game.stage.machine;

import jokatu.game.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class SequentialStageMachine implements StageMachine {

	private int currentIndex = 0;
	private Stage<?> currentStage;
	private final List<StageFactory> factories;

	public SequentialStageMachine(@NotNull StageFactory... factories) {
		this.factories = Arrays.asList(factories);
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
