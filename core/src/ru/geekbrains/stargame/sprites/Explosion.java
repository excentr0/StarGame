package ru.geekbrains.stargame.sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.exceptions.GameException;

public class Explosion extends Sprite {
  private static final float ANIMATE_INTERVAL = 0.017f;
  private final        Sound explosionSound;
  private              float animateTimer;

  public Explosion(TextureAtlas atlas,
                   Sound explosionSound) throws GameException {
    super(atlas.findRegion("explosion"), 9, 9, 74);
    this.explosionSound = explosionSound;
  }

  public void set(Vector2 position,
                  float height) {
    this.position.set(position);
    setHeightProportion(height);
    explosionSound.play();
  }

  @Override
  public void update(float delta) {
    animateTimer += delta;
    if (animateTimer >= ANIMATE_INTERVAL) {
      animateTimer = 0f;
      if (++frame == regions.length) {
        destroy();
      }
    }
  }

  @Override
  public void destroy() {
    super.destroy();
    frame = 0;
  }

}
