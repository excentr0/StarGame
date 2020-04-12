package ru.geekbrains.stargame.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.exceptions.GameException;
import ru.geekbrains.stargame.math.Rect;

public class GameOver extends Sprite {
  public GameOver(final TextureAtlas atlas) throws GameException {
    super(atlas.findRegion("message_game_over"));
  }

  @Override
  public void resize(final Rect worldBounds) {
    setHeightProportion(0.07f);
    setTop(0.1f);
  }
}
