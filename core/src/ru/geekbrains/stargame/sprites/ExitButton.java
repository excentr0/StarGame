package ru.geekbrains.stargame.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.geekbrains.stargame.base.ScaledButton;
import ru.geekbrains.stargame.exceptions.GameException;
import ru.geekbrains.stargame.math.Rect;

public class ExitButton extends ScaledButton {

  public ExitButton(TextureAtlas atlas) throws GameException {
    super(atlas.findRegion("btExit"));
  }

  @Override
  public void resize(Rect worldBounds) {
    setHeightProportion(0.2f);
    setRight(worldBounds.getRight() - 0.05f);
    setBottom(worldBounds.getBottom() + 0.05f);
  }

  @Override
  public void action() {
    Gdx.app.exit();
  }
}
