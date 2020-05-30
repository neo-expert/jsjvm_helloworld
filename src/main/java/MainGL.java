import js.*;
import js.dom.*;
import js.webgl.*;

public class MainGL implements Runnable{
	float xrot=0;
	float yrot=0;
	float[] pMatrix;
	Matrix4f mMatrix;
	Object positionsBuffer;
	Object cubeVerticesColorBuffer;
	Object cubeVerticesIndexBuffer;
	Object shaderProgram;
	int vertexPosition;
	int vertexColor;
	Object projectionMatrix;
	Object modelViewMatrix;
	private GL gl;
	public MainGL(){
		DOMElement canvas=DOM.createElement("canvas");
		DOMElement body=DOM.getElementById("body");
		body.appendChild(canvas);
		gl=new GL(canvas);
	}
	public static void main(String[] args){
		new MainGL().startWEBGL();
		Console.log("before eventloop");
	}

	public void drawScene(GL hl){
		gl.clearDepth(1.0f);                 // Clear everything

		// Clear the canvas before we start drawing on it.

		gl.clear(GL.COLOR_BUFFER_BIT | GL.DEPTH_BUFFER_BIT);

		 gl.bindBuffer(GL.ELEMENT_ARRAY_BUFFER,cubeVerticesIndexBuffer);

		 gl.useProgram(shaderProgram);
		 gl.uniformMatrix4fv(
				 projectionMatrix,
				 false,
				 pMatrix);
		 xrot=0.1f;
		 yrot=0.2f;
		mMatrix.rotate(xrot,1,0,0);
		mMatrix.rotate(yrot,0,1,0);
		 gl.uniformMatrix4fv(
				 modelViewMatrix,
				 false,
				 mMatrix.getArray());
		  int vertexCount = 36;
		  int offset = 0;
		  gl.drawElements(GL.TRIANGLES, vertexCount, GL.UNSIGNED_SHORT, offset);
	}

	public void startWEBGL(){
		//Image img=DOM.createImage();
		GL gl=this.gl;
		float[] positions = new float[]{
			// vordere Fläche
			-1.0f, -1.0f,  1.0f, 0.0f, 0.0f,
			1.0f, -1.0f,  1.0f, 0.0f, 1.0f,
			1.0f,  1.0f,  1.0f, 1.0f, 0.0f,
			-1.0f,  1.0f,  1.0f,1.0f, 1.0f,

			// hintere Fläche
			-1.0f, -1.0f, -1.0f, 0.0f, 0.0f,
			-1.0f,  1.0f, -1.0f,0.0f, 1.0f,
			1.0f,  1.0f, -1.0f,1.0f, 0.0f,
			1.0f, -1.0f, -1.0f,1.0f, 1.0f,

			// obere Fläche
			-1.0f,  1.0f, -1.0f, 0.0f, 0.0f,
			-1.0f,  1.0f,  1.0f,0.0f, 1.0f,
			1.0f,  1.0f,  1.0f,1.0f, 0.0f,
			1.0f,  1.0f, -1.0f,1.0f, 1.0f,

			// untere Fläche
			-1.0f, -1.0f, -1.0f, 0.0f, 0.0f,
			1.0f, -1.0f, -1.0f,0.0f, 1.0f,
			1.0f, -1.0f,  1.0f,1.0f, 0.0f,
			-1.0f, -1.0f,  1.0f,1.0f, 1.0f,

			// rechte Fläche
			1.0f, -1.0f, -1.0f, 0.0f, 0.0f,
			1.0f,  1.0f, -1.0f,0.0f, 1.0f,
			1.0f,  1.0f,  1.0f,1.0f, 0.0f,
			1.0f, -1.0f,  1.0f,1.0f, 1.0f,

			// linke Fläche
			-1.0f, -1.0f, -1.0f, 0.0f, 0.0f,
			-1.0f, -1.0f,  1.0f,0.0f, 1.0f,
			-1.0f,  1.0f,  1.0f,1.0f, 0.0f,
			-1.0f,  1.0f, -1.0f,1.0f, 1.0f
		};
		positionsBuffer = gl.createBuffer();

		gl.bindBuffer(GL.ARRAY_BUFFER, positionsBuffer);
		gl.bufferData(GL.ARRAY_BUFFER, positions, GL.STATIC_DRAW);

		short[] cubeVertexIndices = new short[]{
			0,  1,  2,      0,  2,  3,    // vorne
			4,  5,  6,      4,  6,  7,    // hinten
			8,  9,  10,     8,  10, 11,   // oben
			12, 13, 14,     12, 14, 15,   // unten
			16, 17, 18,     16, 18, 19,   // rechts
			20, 21, 22,     20, 22, 23    // links
		};

		


		float[] colors = new float[]{
			1,0,0,1,
			0,1,0,1,
			0,0,0,1,
			1,1,0,1,

			0,0,0,1,
			1,0,0,1,
			1,0,0,1,
			1,0,0,1,

			1,0,0,1,
			1,1,0,1,
			0,0,0,1,
			0,0,1,1,

			0,0,0,1,
			0,1,0,1,
			0,1,1,1,
			0,0,0,1,

			1,0,1,1,
			0,0,0,1,
			0,0,0,1,
			0,0,0,1,

			0,1,0,1,
			1,0,1,1,
			1,1,0,1,
			0,0,0,1
		};

		cubeVerticesColorBuffer = gl.createBuffer();

		gl.bindBuffer(GL.ARRAY_BUFFER, cubeVerticesColorBuffer);

		gl.bufferData(GL.ARRAY_BUFFER, colors, GL.STATIC_DRAW);

		cubeVerticesIndexBuffer = gl.createBuffer();
		gl.bindBuffer(GL.ELEMENT_ARRAY_BUFFER, cubeVerticesIndexBuffer);

		gl.bufferData(GL.ELEMENT_ARRAY_BUFFER,cubeVertexIndices, GL.STATIC_DRAW);
		Console.log("arrays initialized");
		Console.log(positions.length);
		Console.log(cubeVertexIndices.length);


		String vsSource = "attribute vec4 aVertexPosition;attribute vec4 aVertexColor;uniform mat4 uModelViewMatrix;uniform mat4 uProjectionMatrix;varying lowp vec4 vColor;void main(void){gl_Position = uProjectionMatrix * uModelViewMatrix * aVertexPosition;vColor = aVertexColor;}";

		String fsSource = "varying lowp vec4 vColor;void main(void){gl_FragColor = vColor;}";



		Object vertexShader = loadShader(gl, GL.VERTEX_SHADER, vsSource);
		Object fragmentShader = loadShader(gl, GL.FRAGMENT_SHADER, fsSource);

		// Create the shader program

		shaderProgram = gl.createProgram();
		gl.attachShader(shaderProgram, vertexShader);
		gl.attachShader(shaderProgram, fragmentShader);
		gl.linkProgram(shaderProgram);


		vertexPosition = gl.getAttribLocation(shaderProgram, "aVertexPosition");
      		vertexColor = gl.getAttribLocation(shaderProgram, "aVertexColor");
      		projectionMatrix = gl.getUniformLocation(shaderProgram, "uProjectionMatrix");
      		modelViewMatrix = gl.getUniformLocation(shaderProgram, "uModelViewMatrix");
		

		 this.pMatrix=new float[]{
			 1.8106601238250732f,
			 0f,
			 0f,
			 0f,
			 0f,
			 2.4142136573791504f,
			 0f,
			 0f,
			 0f,
			 0f,
			 -1.0020020008087158f,
			 -1,
			 0f,
			 0f,
			 -0.20020020008087158f,
			 0f
		 };
		 this.mMatrix=new Matrix4f();
		 Console.log("mMatrix");
		 Console.log(mMatrix.getArray()[0]);
		 mMatrix.translate(0,0,-6);
		 Console.log("mMatrix 1");
		 Console.log(mMatrix.getArray()[0]);
		gl.enable(GL.DEPTH_TEST);           // Enable depth testing
		gl.depthFunc(GL.LEQUAL);            // Near things obscure far things

		 {
			 int numComponents = 3;
			 int type = GL.FLOAT;
			 boolean normalize = false;
			 int stride = 5*4;
			 int offset = 0;
			 gl.bindBuffer(GL.ARRAY_BUFFER, positionsBuffer);
			 gl.vertexAttribPointer(
					 vertexPosition,
					 numComponents,
					 type,
					 normalize,
					 stride,
					 offset);
			 gl.enableVertexAttribArray(
					 vertexPosition);
		 }
		 // Tell WebGL how to pull out the colors from the color buffer
		 // into the vertexColor attribute.
		 {
			 int numComponents = 4;
			 int type = GL.FLOAT;
			 boolean normalize = false;
			 int stride = 0;
			 int offset = 0;
			 gl.bindBuffer(GL.ARRAY_BUFFER, cubeVerticesColorBuffer);
			 gl.vertexAttribPointer(
					 vertexColor,
					 numComponents,
					 type,
					 normalize,
					 stride,
					 offset);
			 gl.enableVertexAttribArray(
					 vertexColor);
		 }
		gl.clearColor(0.0f, 0.0f, 0.0f, 1.0f);  // Clear to black, fully opaque
		DOM.requestAnimationFrame(this);
		while(true){
			Runnable ev=DOM.getNextEvent();
			ev.run();
		}
	}

	public static Object loadShader(GL gl, int type, String source){
		Object shader = gl.createShader(type);
		gl.shaderSource(shader, source);
		gl.compileShader(shader);
		return shader;
	}

	//EventHandler for animations Frame reqquest:
	public void run() {
		drawScene(gl);
		DOM.requestAnimationFrame(this);
	}
}
