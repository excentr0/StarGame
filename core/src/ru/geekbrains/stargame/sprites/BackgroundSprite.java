package ru.geekbrains.stargame.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.exceptions.GameException;
import ru.geekbrains.stargame.math.Rect;

public class BackgroundSprite extends Sprite {
  public BackgroundSprite(TextureRegion texture) throws GameException {
    super(new TextureRegion(texture));
    this.setSize(1f, 1f);
  }

  @Override
  public void resize(final Rect worldBounds) {
    setHeightProportion(1f);
    position.set(worldBounds.position);
  }
}
