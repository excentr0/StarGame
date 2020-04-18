package ru.geekbrains.stargame.sprites;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.geekbrains.stargame.base.ScaledButton;
import ru.geekbrains.stargame.exceptions.GameException;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.screen.GameScreen;

public class PlayButton extends ScaledButton {

  private final Game game;

  public PlayButton(TextureAtlas atlas,
                    Game game) throws GameException {
    super(atlas.findRegion("btPlay"));
    this.game = game;
  }

  @Override
  public void resize(Rect worldBounds) {
    setHeightProportion(0.25f);
    setLeft(worldBounds.getLeft() + 0.05f);
    setBottom(worldBounds.getBottom() + 0.05f);
  }

  @Override
  public void action() {
    game.setScreen(new GameScreen(game));
  }
}
