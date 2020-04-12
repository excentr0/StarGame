package ru.geekbrains.stargame.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.exceptions.GameException;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;
import ru.geekbrains.stargame.pool.ExplosionPool;
import ru.geekbrains.stargame.sprites.Bullet;
import ru.geekbrains.stargame.sprites.Explosion;

public abstract class Ship extends Sprite {

  protected Vector2 v;
  protected Vector2 v0;
  protected Vector2 bulletV;

  protected Rect  worldBounds;
  protected Sound shootSound;

  protected BulletPool      bulletPool;
  protected ExplosionPool   explosionPool;
  protected TextureRegion[] bulletRegions;

  protected int damage;
  protected int hp;

  protected float bulletHeight;
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
      performShooting(delta);
    }
  }

  /**
   * Выполняем стрельбу
   *
   * @param delta дельта времени
   */
  protected void performShooting(final float delta) {
    reloadTimer += delta;
    if (reloadTimer >= reloadInterval) {
      reloadTimer = 0f;
      shoot();
    }
  }

  protected void shoot() {
    Bullet bullet = bulletPool.obtain();
    bullet.set(this, bulletRegions, position, bulletV, bulletHeight, worldBounds, damage);
    shootSound.play(1f);
  }

  @Override
  public void destroy() {
    super.destroy();
    boom();
  }

  public void damage(int damage) {
    hp -= damage;
    if (hp <= 0) {
      hp = 0;
      destroy();
    }
  }

  private void boom() {
    Explosion explosion = explosionPool.obtain();
    explosion.set(position, getHeight());
  }

  public int getDamage() {
    return damage;
  }
}
