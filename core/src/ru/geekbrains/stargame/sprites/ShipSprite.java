package ru.geekbrains.stargame.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.exceptions.GameException;
import ru.geekbrains.stargame.math.Rect;

public class ShipSprite extends Sprite {
  private final float speed = 0.1f;
  private Vector2 newPosition = new Vector2();
  private Vector2 dir = new Vector2();

  public ShipSprite(TextureAtlas atlas) throws GameException {
    super(atlas.findRegion("ship", 1));
  }

  @Override
  public void update(final float delta) {
    // Как далеко может сместиться спрайт за дельту
    float maxDistance = speed * Gdx.graphics.getDeltaTime();
    if (newPosition.dst(position) >= maxDistance) {
      position.mulAdd(dir, delta * speed);
    } else {
      dir.setZero();
    }

    final boolean left = Gdx.input.isKeyPressed(Input.Keys.LEFT);
    final boolean right = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
    final boolean up = Gdx.input.isKeyPressed(Input.Keys.UP);
    final boolean down = Gdx.input.isKeyPressed(Input.Keys.DOWN);

    float speedX = speed * delta;
    if (left) {
      speedX *= -1f;
    } else {
      speedX *= right ? 1f : 0f;
    }

    float speedY = speed * delta;
    if (down) {
      speedY *= -1f;
    } else {
      speedY *= up ? 1f : 0f;
    }

    position.add(speedX, speedY);
  }

  @Override
  public void resize(final Rect worldBounds) {
    this.position.set(worldBounds.position).sub(0, 0.2f);
    this.setHeightProportion(0.07f);
  }

  /**
   * Вычисляем вектор направления от текущей позиции корабля к точке касания экрана
   *
   * @param touch вектор касания экрана
   * @return false
   */
  @Override
  public boolean touchDown(final Vector2 touch, final int pointer, final int button) {
    newPosition = touch.cpy();
    dir.set(touch.sub(position).nor());
    return false;
  }
}
