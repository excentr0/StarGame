package ru.geekbrains.stargame.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.exceptions.GameException;
import ru.geekbrains.stargame.math.Rect;

public class ShipSprite extends Sprite {
  private final float speed = 20f;
  private Vector2 shipPosition = new Vector2();
  private Vector2 newPosition = new Vector2();
  private Vector2 dir = new Vector2();

  public ShipSprite(Texture texture) throws GameException {
    super(new TextureRegion(texture));
    this.setSize(0.07f, 0.07f);
  }

  @Override
  public void update(final float delta) {
    // Проверки, что бы корабль останавливался в точке клика
    if ((int) shipPosition.x != (int) newPosition.x) {
      shipPosition.x += dir.x * speed * delta;
    }
    if ((int) shipPosition.y != (int) newPosition.y) {
      shipPosition.y += dir.y * speed * delta;
    }
  }

  @Override
  public void resize(final Rect worldBounds) {
    setHeightProportion(1f);
    position.set(worldBounds.position);
  }

  @Override
  public boolean touchDown(final Vector2 touch, final int pointer, final int button) {
    // Инвертируем ось Y
    newPosition.set(touch.x, Gdx.graphics.getHeight() - touch.y);
    // Вычитаем векторы и нормализуем, что бы получить направление
    dir = newPosition.cpy().sub(shipPosition).nor();
    return true;
  }
}
