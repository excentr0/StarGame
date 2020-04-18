package ru.geekbrains.stargame.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import static com.badlogic.gdx.graphics.Texture.TextureFilter.Linear;

public class Font extends BitmapFont {

  public Font(final String fontFile,
              final String imageFile) {
    super(Gdx.files.internal(fontFile), Gdx.files.internal(imageFile), false, false);
    getRegion().getTexture().setFilter(Linear, Linear);
  }

  public GlyphLayout draw(final Batch batch,
                          final CharSequence str,
                          final float x,
                          final float y,
                          final int halign) {
    return super.draw(batch, str, x, y, 0, halign, false);
  }

  public void setSize(float size) {
    getData().setScale(size / getCapHeight());
  }
}
