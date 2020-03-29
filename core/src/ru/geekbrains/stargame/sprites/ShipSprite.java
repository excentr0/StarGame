package ru.geekbrains.stargame.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.exceptions.GameException;
import ru.geekbrains.stargame.math.Rect;

public class ShipSprite extends Sprite {
  private final float speed = 0.05f;
  private Vector2 newPosition = new Vector2();
  private Vector2 dir = new Vector2();

  public ShipSprite(Texture texture) throws GameException {
    super(new TextureRegion(texture));
    this.setSize(0.07f, 0.07f);
  }

  @Override
  public void update(final float delta) {
    if (newPosition.dst(position) > 0.001f) {
      position.mulAdd(dir, delta * speed);
    }
  }

  @Override
  public void resize(final Rect worldBounds) {
    position.set(worldBounds.position);
  }

  public boolean calcNewPosition(final Vector2 touch) {
    newPosition = touch.cpy();
    dir.set(touch.sub(position).nor());
    return false;
  }
}
