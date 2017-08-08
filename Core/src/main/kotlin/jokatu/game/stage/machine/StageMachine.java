package jokatu.game.stage.machine;

import jokatu.game.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface StageMachine {

	@Nullable
	Stage<?> getCurrentStage();

	@NotNull
	Stage<?> advance();
}
