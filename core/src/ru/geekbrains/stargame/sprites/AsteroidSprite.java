package ru.geekbrains.stargame.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.exceptions.GameException;

public class AsteroidSprite extends Sprite {

  public AsteroidSprite(final TextureAtlas atlas) throws GameException {
    super(atlas.findRegion("asteroid"));
  }
}
