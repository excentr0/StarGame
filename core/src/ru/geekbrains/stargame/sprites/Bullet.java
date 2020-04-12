package ru.geekbrains.stargame.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;

public class Bullet extends Sprite {
  private final Vector2 speedVector = new Vector2();
  private       int     damage;

  private Rect   worldBounds;
  private Object owner;

  public Bullet() {
    regions = new TextureRegion[2];
  }

  public void set(
      Object owner,
      TextureRegion[] regions,
      Vector2 pos0,
      Vector2 v0,
      float height,
      Rect worldBounds,
      int damage) {
    this.owner   = owner;
    this.regions = regions;
    this.position.set(pos0);
    this.speedVector.set(v0);
    setHeightProportion(height);
    this.worldBounds = worldBounds;
    this.damage      = damage;
  }

  @Override
  public void update(float delta) {
    position.mulAdd(speedVector, delta);
    if (isOutside(worldBounds)) destroy();
  }

  public int getDamage() {
    return damage;
  }

  public Object getOwner() {
    return owner;
  }
}
