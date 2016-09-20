package Recipe;

public class Recipe {
	String name;
	String preptime;
	String cooktime;
	String servings;
	String rating;
	String ingreidents;
	String directions;
	String categories;
	int hasPic; 
	
	public Recipe(String name, String preptime, String cooktime, String servings, String rating, String ingredients, String directions, String categories, int hasPic ){
		this.name = name;
		this.preptime = preptime;
		this.cooktime = cooktime;
		this.servings = servings;
		this.rating = rating; 	
		this.ingreidents = ingredients;
		this.directions = directions;
		this.categories = categories;
		this.hasPic = hasPic;
	}

	public String getName(){
		return this.name;
	}
	
	public String getPreptime(){
		return this.preptime;
		
	}
	public String getCooktime(){
		return this.cooktime;
		
	}public String getServings(){
		return this.servings;
		
	}public String getRating(){
		return this.rating;
		
	}
	public String getIngredients(){
		return this.ingreidents;
		
	}
	
	public String getDirections(){
		return this.directions;
	}
	
	public String getCategories(){
		return this.categories;
	}

	public int getPicStatus() {
		return this.hasPic;
		
	}
}
