package scrape;

import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.tinify.Options;
import com.tinify.Source;
import com.tinify.Tinify;
import Recipe.Recipe;

public class webScrap {

	static String excelFilePath = "XXXXXXXXXXXXXXXXXXXXXXXX/Workbook1.xlsx";
	static String folderPath = "XXXXXXXXXXXXXXXXXXXXXXXXXXX/RecipeImgs/";

	public static void main(String[] args) throws IOException {

		Tinify.setKey("XXXXXXXXXXXXXXXXXXXX");

		String url = "http://www.XXXXXXXXXXXX/recipe/";

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("RecipeData");

		int rowCount = 0;
		String source = null;
		int hasPic = 0;

		System.out.println("Starting to Scrape Web Pages for Recipes.....");

		for (int i = 1873; i < 2000; i++) {

			String newurl = url + i;
			print("Fetching %s...", newurl);

			Document doc = Jsoup.connect(newurl).get();
			Elements name = doc.select("h3.recipe-name");
			Elements pic = doc.select("img.recipe-image.row");
			Elements preptime = doc.select("li.small-6.medium-6.large-6.columns.prep-time");
			Elements cooktime = doc.select("li.small-6.medium-6.large-6.columns.cook-time");
			Elements servings = doc.select("li.small-6.medium-6.large-6.columns.servings");
			Elements ratings = doc.select("li.small-6.medium-6.large-6.columns.rating");
			Elements ingredients = doc.select("li.ingredient");
			Elements directions = doc.select("div.directions");
			Elements categories = doc.select("li.tag-item");

			String page = doc.location();
			String check = "http://www.XXXXXXXXXXX";

			if (page.equals(check)) {
				// If recipe doesn't exist, webhost sends it back to landing
				// page...
				// Therefore want to skip this cyle.

				continue;
			}

			if (ingredients.size() == 0) {
				// Some page elements differ throughout pages
				ingredients = doc.select("div.ingredients");
			}

			if (pic.size() > 0) {
				// Set variable to 1 in excel
				hasPic = 1;
				for (Element img : pic) {
					source = img.absUrl("src");
				}
			} else {
				hasPic = 0;
			}

			Recipe recipe = new Recipe(name.text(), preptime.text(), cooktime.text(), servings.text(), ratings.text(),
					ingredients.text(), directions.text(), categories.text(), hasPic);

			// Created new file name for specific recipe
			if (hasPic == 1) {
				String FileLocation = folderPath + recipe.getName() + ".jpg";
				Source sourceURL = Tinify.fromUrl(source);
				Options options = new Options().with("method", "fit").with("width", 425).with("height", 425);
				Source resized = sourceURL.resize(options);
				resized.toFile(FileLocation);
			}
			Row row = sheet.createRow(++rowCount);
			writeBook(recipe, row);

			try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
				workbook.write(outputStream);
				outputStream.close();
			}
		}

		workbook.close();

		System.out.println("Web Scrap Completed...");

	}

	private static void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

	private static void writeBook(Recipe aRecipe, Row row) {
		Cell cell = row.createCell(1);
		cell.setCellValue(aRecipe.getName());

		cell = row.createCell(2);
		cell.setCellValue(aRecipe.getPreptime());

		cell = row.createCell(3);
		cell.setCellValue(aRecipe.getCooktime());

		cell = row.createCell(4);
		cell.setCellValue(aRecipe.getServings());

		cell = row.createCell(5);
		cell.setCellValue(aRecipe.getRating());
		cell = row.createCell(6);
		cell.setCellValue(aRecipe.getIngredients());

		cell = row.createCell(7);
		cell.setCellValue(aRecipe.getDirections());

		cell = row.createCell(8);
		cell.setCellValue(aRecipe.getCategories());

		cell = row.createCell(9);
		cell.setCellValue(aRecipe.getPicStatus());

	}

}