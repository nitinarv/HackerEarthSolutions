package aug162015;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class KingByteLandProblem {
	static KingByteLandProblem instance ;

	public static void main(String args[] ) throws Exception {
		/*
		 * Read input from stdin and provide input before running
		 */
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = br.readLine();
		int testcases = getInt(line);

		
		instance = new KingByteLandProblem();

		List<String> resultOutput = new ArrayList<String>();
		
		
		for(int k = 0; k < testcases; k++){
			
			br = new BufferedReader(new InputStreamReader(System.in));
			line = br.readLine();
			int numberofenvelops = getInt(line);


			List<Plot> plotLists = new ArrayList<Plot>();

			for (int i = 0; i < numberofenvelops; i++){

				br = new BufferedReader(new InputStreamReader(System.in));
				line = br.readLine();
				String[] co_ordinates = line.split(" ");
				if(co_ordinates.length==4){
					//Valid
					Plot plot = instance.new Plot(co_ordinates[0],co_ordinates[1],co_ordinates[2],co_ordinates[3]);
					plotLists.add(plot);
				}
			}


			if(plotLists.size()>1){
				double totalArea = 0;
				for(int i = 0; i<plotLists.size(); i++){
					totalArea += plotLists.get(i).getArea();	
					if(i>=1){
						totalArea -= plotLists.get(0).getUnionArea(plotLists.get(i));

					}
				}
				resultOutput.add(""+(int)totalArea);
			}
		}
		
		
		
		for(String result: resultOutput){
			System.out.println(result);
		}
	}


	public static int getInt(String line){
		if(line!=null && line.trim().toCharArray().length>0){
			if(stringIsDigit(line.trim())){
				return Integer.parseInt(line.trim());
			}else{
				return 0;
			}
		}else{
			return 0;
		}
	}
	
	public static boolean stringIsDigit(String line){
		for(Character digit: line.trim().toCharArray()){
			if(!Character.isDigit(digit)){
				return false;
			}
		}
		return true;
	}


	public class Plot{
		int a;
		int b;
		int c;
		int d;

		DataPoints[] plotDatapoints = new DataPoints[4];
		Rectangle rectangle;

		public Plot(String mA, String mB, String mC, String mD){
			a = Integer.parseInt(mA);
			b = Integer.parseInt(mB);
			c = Integer.parseInt(mC);
			d = Integer.parseInt(mD);

			plotDatapoints[0] = new DataPoints(a, d);
			plotDatapoints[1] = new DataPoints(a, b);
			plotDatapoints[2] = new DataPoints(c, b);
			plotDatapoints[3] = new DataPoints(c, d);
			rectangle = new Rectangle(plotDatapoints[0].x, plotDatapoints[0].y, (int)getWidth(), (int)getHeight());
		}

		public double getWidth(){
			int dx = (plotDatapoints[0].x - plotDatapoints[3].x);
			int dy = (plotDatapoints[0].y - plotDatapoints[3].y);
			int dx2 = (dx*dx);
			int dy2 = (dy*dy);
			return Math.sqrt((dx2>dy2)?(dx2-dy2):(dy2-dx2));
		}

		public double getHeight(){
			int dx = (plotDatapoints[0].x-plotDatapoints[1].x);
			int dy = (plotDatapoints[0].y-plotDatapoints[1].y);
			int dx2 = (dx*dx);
			int dy2 = (dy*dy);
			return Math.sqrt((dx2>dy2)?(dx2-dy2):(dy2-dx2));
		}

		public double getArea(){
			double width = getWidth();
			double height = getHeight();
			return (width * height);
		}


		public boolean doesIntersect(Plot mPlot){
			return rectangle.intersects(mPlot.rectangle);
		}

		public double getUnionArea(Plot mPlot){
			Rectangle intersection = rectangle.intersection(mPlot.rectangle);
			double area = intersection.getSize().getWidth() * intersection.getSize().getHeight();
			if(doesIntersect(mPlot)){
				return area;
			}else{
				return 0D;
			}
		}
	}


	public class DataPoints{
		public int x;
		public int y;

		public DataPoints(int mX, int mY){
			x = mX;
			y = mY;
		}
	}


}
