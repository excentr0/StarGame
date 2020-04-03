package ru.geekbrains.stargame.sprites;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.exceptions.GameException;
import ru.geekbrains.stargame.math.Rect;

public class ShipSprite extends Sprite {
  private final float speed;

  private boolean up = false;
  private boolean right = false;
  private boolean down = false;
  private boolean left = false;
  private float maxDistance;

  private Vector2 newPosition = new Vector2();
  private Vector2 dir = new Vector2();

  public ShipSprite(TextureAtlas atlas) throws GameException {
    super(atlas.findRegion("ship", 1));
    speed = 0.1f;
  }

  /**
   * обновляем позицию корабля
   *
   * @param delta дельта времени
   */
  @Override
  public void update(final float delta) {

    // вычисляем только если нажата какая-то клавиша
    updateIfKeyPressed();

    // Как далеко может сместиться спрайт за дельту
    maxDistance = speed * delta;
    if (newPosition.dst(position) >= maxDistance) {
      position.mulAdd(dir, maxDistance);
    } else {
      dir.setZero();
    }
  }

  private void updateIfKeyPressed() {
    if (left || right || up || down) {
      float speedX = left ? -1f : right ? 1f : 0f;
      float speedY = down ? -1f : up ? 1f : 0f;
      dir.set(speedX, speedY);
    }
  }

  /**
   * Обрабатывает нажатие кнопок стрелок
   *
   * @param keycode код нажатой клавиши
   */
  public boolean keyDown(int keycode) {

    if (keycode == Keys.LEFT) {
      left = true;
    } else if (keycode == Keys.RIGHT) {
      right = true;
    } else if (keycode == Keys.UP) {
      up = true;
    } else if (keycode == Keys.DOWN) {
      down = true;
    }

    return false;
  }

  /**
   * Обрабатывает отпускание клавиш
   *
   * @param keycode код отпущенной клавиши
   */
  public boolean keyUp(int keycode) {
    if (keycode == Keys.LEFT) {
      left = false;
    } else if (keycode == Keys.RIGHT) {
      right = false;
    } else if (keycode == Keys.UP) {
      up = false;
    } else if (keycode == Keys.DOWN) {
      down = false;
    }
    System.out.println(keycode + " is keyUp");
    // Обнуляем вектор, если все клавиши стрелок отпущены
    if (!up && !down && !left && !right) {
      dir.setZero();
    }
    return false;
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
