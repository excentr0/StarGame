package ru.geekbrains.stargame.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.exceptions.GameException;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.math.Rnd;

public class SmallAsteroidSprite extends Sprite {

  private final float asteroidSize = Rnd.nextFloat(0.01f, 0.02f);

  private Vector2 speedVector;
  private Rect worldBounds;

  public SmallAsteroidSprite(final TextureAtlas atlas) throws GameException {
    super(atlas.findRegion("asteroid"));
    float vx = Rnd.nextFloat(-0.005f, 0.005f);
    float vy = Rnd.nextFloat(-0.05f, -0.1f);
    speedVector = new Vector2(vx, vy);
  }

  @Override
  public void update(float delta) {
    position.mulAdd(speedVector, delta * 0.7f);

    checkBounds();
  }

  private void checkBounds() {
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
    setHeightProportion(asteroidSize);
    float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
    float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
    this.position.set(posX, posY);
  }
}
