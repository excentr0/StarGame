package ru.geekbrains.stargame.sprites;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.base.Ship;
import ru.geekbrains.stargame.exceptions.GameException;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;

public class MainShip extends Ship {

  private static final float BOTTOM_MARGIN = 0.05f;
  private static final float SHIP_HEIGHT   = 0.07f;

  private Vector2 newPosition = new Vector2();
  private Vector2 dir         = new Vector2();

  private boolean up    = false;
  private boolean right = false;
  private boolean down  = false;
  private boolean left  = false;

  public MainShip(TextureAtlas atlas,
                  BulletPool bulletPool,
                  Sound shootSound)
      throws GameException {
    super(atlas.findRegion("playerShip1_green"));
    this.bulletPool = bulletPool;
    this.shootSound = shootSound;

    bulletRegion   = atlas.findRegion("laserGreen06");
    bulletV        = new Vector2(0, 0.5f);
    v0             = new Vector2(0.5f, 0);
    v              = new Vector2();
    bulletHeight   = 0.02f;
    reloadInterval = 0.2f;
    reloadTimer    = reloadInterval;
    damage         = 1;
    hp             = 100;
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
    checkPressedButton(anyKeyPressed);

    // Как далеко может сместиться спрайт за дельту
    final float speed = 0.1f;
    final float maxDistance = speed * delta;
    if (newPosition.dst(position) >= maxDistance || anyKeyPressed) {
      position.mulAdd(dir, maxDistance);
    } else {
      stop();
    }
    // Стреляем
    reloadTimer += delta;
    if (reloadTimer >= reloadInterval) {
      reloadTimer = 0f;
      shoot();
    }

    checkBounds();
  }

  /**
   * Проверяем какие из стрелок нажаты
   *
   * @param anyKeyPressed нажата ли какая-то стрелка
   */
  private void checkPressedButton(final boolean anyKeyPressed) {
    if (anyKeyPressed) {
      float speedX;
      float speedY;

      if (left) {
        speedX = -1f;
      } else {
        speedX = right ? 1f : 0f;
      }

      if (down) {
        speedY = -1f;
      } else {
        speedY = up ? 1f : 0f;
      }
      dir.set(speedX, speedY);
    }
  }

  /**
   * Останавливаем корабль
   */
  private void stop() {
    dir.setZero();
  }

  /**
   * Проверим, что бы корабль не вылетел за пределы экрана
   */
  private void checkBounds() {
    if (getLeft() < worldBounds.getLeft()) {
      setLeft(worldBounds.getLeft());
      stop();
    }
    if (getRight() > worldBounds.getRight()) {
      setRight(worldBounds.getRight());
      stop();
    }
    if (getTop() > worldBounds.getTop()) {
      setTop(worldBounds.getTop());
      stop();
    }
    if (getBottom() < worldBounds.getBottom()) {
      setBottom(worldBounds.getBottom());
      stop();
    }
  }

  @Override
  public void resize(final Rect worldBounds) {
    this.worldBounds = worldBounds;
    this.setHeightProportion(SHIP_HEIGHT);
    setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
  }

  /**
   * Вычисляем вектор направления от текущей позиции корабля к точке касания экрана
   *
   * @param touch вектор касания экрана
   * @return false
   */
  @Override
  public boolean touchDown(final Vector2 touch,
                           final int pointer,
                           final int button) {
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
      stop();
    }
    return false;
  }

  public void dispose() {
    shootSound.dispose();
  }
}
