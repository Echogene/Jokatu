package jokatu.game.stage.machine;

import jokatu.game.stage.Stage;
import org.jetbrains.annotations.NotNull;

public interface StageFactory {

	@NotNull
	Stage<?> produce();
}
