package ru.geekbrains.stargame.sprites;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.exceptions.GameException;
import ru.geekbrains.stargame.math.Rect;

public class ShipSprite extends Sprite {

  private boolean up = false;
  private boolean right = false;
  private boolean down = false;
  private boolean left = false;

  private Vector2 newPosition = new Vector2();
  private Vector2 dir = new Vector2();
  private Rect worldBounds = new Rect();

  public ShipSprite(TextureAtlas atlas) throws GameException {
    super(atlas.findRegion("ship", 1));
  }

  /**
   * обновляем позицию корабля
   *
   * @param delta дельта времени
   */
  @Override
  public void update(final float delta) {

    // вычисляем только если нажата какая-то клавиша
    boolean anyKeyPressed = left || right || up || down;
    if (anyKeyPressed) {
      float speedX = left ? -1f : right ? 1f : 0f;
      float speedY = down ? -1f : up ? 1f : 0f;
      dir.set(speedX, speedY);
    }

    // Как далеко может сместиться спрайт за дельту
    final float speed = 0.1f;
    final float maxDistance = speed * delta;
    if (newPosition.dst(position) >= maxDistance || anyKeyPressed) {
      position.mulAdd(dir, maxDistance);
    } else {
      dir.setZero();
    }
  }

  @Override
  public void resize(final Rect worldBounds) {
    this.worldBounds = worldBounds;
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
}
