package ru.geekbrains.stargame.pool;

import ru.geekbrains.stargame.base.SpritesPool;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.sprites.Enemy;

public class EnemyPool extends SpritesPool<Enemy> {
  private final BulletPool    bulletPool;
  private final ExplosionPool explosionPool;
  private final Rect          worldBounds;

  public EnemyPool(BulletPool bulletPool,
                   final ExplosionPool explosionPool, Rect worldBounds) {
    this.bulletPool    = bulletPool;
    this.explosionPool = explosionPool;
    this.worldBounds   = worldBounds;
  }

  @Override
  protected Enemy newObject() {
    return new Enemy(bulletPool, explosionPool,worldBounds);
  }
}
