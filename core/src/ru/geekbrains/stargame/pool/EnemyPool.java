package ru.geekbrains.stargame.pool;

import ru.geekbrains.stargame.base.SpritesPool;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.sprites.Enemy;

public class EnemyPool extends SpritesPool<Enemy> {
  private BulletPool bulletPool;
  private Rect       worldBounds;

  public EnemyPool(BulletPool bulletPool,
                   Rect worldBounds) {
    this.bulletPool  = bulletPool;
    this.worldBounds = worldBounds;
  }

  @Override
  protected Enemy newObject() {
    return new Enemy(bulletPool, worldBounds);
  }
}
