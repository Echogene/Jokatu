package jokatu.game.games.uzta;

import jokatu.components.GameComponent;
import jokatu.components.config.GameConfiguration;
import jokatu.game.games.uzta.game.UztaFactory;
import jokatu.game.games.uzta.input.RandomiseGraphInputDeserialiser;
import jokatu.game.games.uzta.input.SelectEdgeInputDeserialiser;
import jokatu.game.games.uzta.player.UztaPlayer;
import jokatu.game.games.uzta.views.UztaViewResolverFactory;
import jokatu.game.input.InputDeserialiser;
import jokatu.game.input.finishstage.EndStageInputDeserialiser;
import jokatu.game.joining.JoinInputDeserialiser;
import jokatu.game.player.PlayerFactory;
import ophelia.collections.BaseCollection;
import ophelia.collections.set.UnmodifiableSet;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static jokatu.game.games.uzta.game.Uzta.UZTA;

/**
 * @author steven
 */
@GameComponent(gameName = UZTA)
public class UztaConfiguration implements GameConfiguration {

	private final UztaFactory uztaFactory;
	private final UztaViewResolverFactory uztaViewResolverFactory;
	private final JoinInputDeserialiser joinInputDeserialiser;
	private final RandomiseGraphInputDeserialiser randomiseGraphInputDeserialiser;
	private final EndStageInputDeserialiser endStageInputDeserialiser;
	private final SelectEdgeInputDeserialiser selectEdgeInputDeserialiser;

	@Autowired
	public UztaConfiguration(UztaFactory uztaFactory,
	                         UztaViewResolverFactory uztaViewResolverFactory,
	                         JoinInputDeserialiser joinInputDeserialiser,
	                         RandomiseGraphInputDeserialiser randomiseGraphInputDeserialiser,
	                         EndStageInputDeserialiser endStageInputDeserialiser,
	                         SelectEdgeInputDeserialiser selectEdgeInputDeserialiser) {
		this.uztaFactory = uztaFactory;
		this.uztaViewResolverFactory = uztaViewResolverFactory;
		this.joinInputDeserialiser = joinInputDeserialiser;
		this.randomiseGraphInputDeserialiser = randomiseGraphInputDeserialiser;
		this.endStageInputDeserialiser = endStageInputDeserialiser;
		this.selectEdgeInputDeserialiser = selectEdgeInputDeserialiser;
	}

	@NotNull
	@Override
	public UztaFactory getGameFactory() {
		return uztaFactory;
	}

	@NotNull
	@Override
	public PlayerFactory<?> getPlayerFactory() {
		return (game, username) -> new UztaPlayer(username);
	}

	@NotNull
	@Override
	public BaseCollection<? extends InputDeserialiser> getInputDeserialisers() {
		return new UnmodifiableSet<>(Arrays.asList(
				randomiseGraphInputDeserialiser,
				joinInputDeserialiser,
				endStageInputDeserialiser,
				selectEdgeInputDeserialiser
		));
	}

	@NotNull
	@Override
	public UztaViewResolverFactory getViewResolverFactory() {
		return uztaViewResolverFactory;
	}
}
