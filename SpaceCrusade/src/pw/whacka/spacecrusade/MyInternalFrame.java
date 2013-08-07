/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package pw.whacka.spacecrusade;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JInternalFrame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/* Used by InternalFrameDemo.java. */
public class MyInternalFrame extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int openFrameCount = 0;
	static final int xOffset = 30, yOffset = 30;

	private BufferedImage backgroundImage;

	public MyInternalFrame() {
		super("Document #" + (++openFrameCount),

		true, // resizable
				true, // closable
				true, // maximizable
				true);// iconifiable

		setSize(300, 300);
		setVisible(true);
		// paintComponent(backgroundImage.getGraphics());

		// Set the window's location.
		setLocation(xOffset * openFrameCount, yOffset * openFrameCount);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Draw the background image.
		g.drawImage(backgroundImage, 0, 0, null);
	}

	public MyInternalFrame(String string) {
		super(string + " #" + (++openFrameCount), true, // resizable
				true, // closable
				true, // maximizable
				true);// iconifiable
		String path = new String ();
		// super("Document #" + (++openFrameCount));

		// ...Create the GUI and put it in the window...

		// ...Then set the window size or call pack...
		setSize(300, 300);
		setVisible(true);

		// Set the window's location.
		setLocation(xOffset * openFrameCount, yOffset * openFrameCount);
	}

	public MyInternalFrame(String string, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
		super(string + " #" + (++openFrameCount), resizable, // resizable
				closable, // closable
				maximizable, // maximizable
				iconifiable);// iconifiable
		
		
		//FileHandle path = Gdx.files.internal("windowmanager/background2.bmp");

/*
		try {
            //path = Gdx.files.internal("windowmanager/background.bmp");
            System.out.println(path);
            backgroundImage = ImageIO.read(Gdx.files.internal("windowmanager/background2.bmp").file());
            //backgroundImage = ImageIO.read(Gdx.files.internal(path).file());
			//backgroundImage = ImageIO.read(new File(path, "/background.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();


		}*/
		// super("Document #" + (++openFrameCount));

		// ...Create the GUI and put it in the window...

		// ...Then set the window size or call pack...
		setSize(300, 300);
		setVisible(true);

		// Set the window's location.
		setLocation(xOffset * openFrameCount, yOffset * openFrameCount);
	}
}
