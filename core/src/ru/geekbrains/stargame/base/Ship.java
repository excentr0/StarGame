package ru.geekbrains.stargame.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.exceptions.GameException;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;
import ru.geekbrains.stargame.sprites.Bullet;

public abstract class Ship extends Sprite {

  protected Vector2 v;
  protected Vector2 v0;
  protected Rect worldBounds;
  protected BulletPool bulletPool;
  protected TextureRegion bulletRegion;
  protected Vector2 bulletV;
  protected Sound shootSound;

  protected int damage;
  protected float bulletHeight;
  protected int hp;
  protected float reloadInterval;
  protected float reloadTimer;

  public Ship() {}

  public Ship(final TextureRegion region) throws GameException {
    super(region);
  }

  @Override
  public void update(final float delta) {
    if (position.y + getHalfHeight() > worldBounds.getTop()) {
      position.mulAdd(v, delta * 2);
      // делаем reloadTimer равным reloadInterval, что бы корабль выстрелил сразу после
      // полного появления на экране
      reloadTimer = reloadInterval;
    } else {
      position.mulAdd(v, delta);
      reloadTimer += delta;
      if (reloadTimer >= reloadInterval) {
        reloadTimer = 0f;
        shoot();
      }
    }
  }

  protected void shoot() {
    Bullet bullet = bulletPool.obtain();
    bullet.set(this, bulletRegion, position, bulletV, bulletHeight, worldBounds, damage);
    shootSound.play(1f);
  }
}
