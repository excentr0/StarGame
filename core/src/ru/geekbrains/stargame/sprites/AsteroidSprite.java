package ru.geekbrains.stargame.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.exceptions.GameException;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.math.Rnd;

public class AsteroidSprite extends Sprite {

  private static final float SIZE = 0.05f;

  private Vector2 v;
  private Rect worldBounds;

  private float animateInterval = 0.5f;
  private float animateTimer;

  public AsteroidSprite(final TextureAtlas atlas) throws GameException {
    super(atlas.findRegion("asteroid"));
    float vx = Rnd.nextFloat(-0.005f, 0.005f);
    float vy = Rnd.nextFloat(-0.05f, -0.1f);
    v = new Vector2(vx, vy);
    animateTimer = Rnd.nextFloat(0, 0.5f);
  }

  @Override
  public void update(float delta) {
    position.mulAdd(v, delta);
    scale += 0.01f;
    animateTimer += delta;
    if (animateTimer >= animateInterval) {
      animateTimer = 0;
      scale = 1;
    }
    if (getTop() < worldBounds.getBottom()) {
      setBottom(worldBounds.getTop());
    }
    if (getLeft() > worldBounds.getRight()) {
      setRight(worldBounds.getLeft());
    }
    if (getRight() < worldBounds.getLeft()) {
      setLeft(worldBounds.getRight());
    }
  }

  @Override
  public void resize(Rect worldBounds) {
    this.worldBounds = worldBounds;
    setHeightProportion(SIZE);
    float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
    float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
    this.position.set(posX, posY);
  }
}
