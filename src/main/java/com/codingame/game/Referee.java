package com.codingame.game;
import java.util.List;

import com.codingame.gameengine.core.AbstractPlayer.TimeoutException;
import com.codingame.gameengine.core.AbstractReferee;
import com.codingame.gameengine.core.SoloGameManager;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.google.inject.Inject;

public class Referee extends AbstractReferee {
	
    @Inject
		private SoloGameManager<Player> gameManager;
    
		@Inject
		private GraphicEntityModule graphicEntityModule;

    @Override
    public void init() {
			// @TODO:
			// upgrade frame duration
			// upgrade timeout
			// get test case
			// mount initial view
    }

    @Override
    public void gameTurn(int turn) {
			// @TODO:
			// get player
			// give input to player
			// read output.s player
			// verify output
			// upgrade view
    }
}
