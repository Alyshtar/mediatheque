package media.servlets.processing;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class DataProcessing {
	
	public static JsonObject getJsonFromRequest(HttpServletRequest request) throws IOException, JsonSyntaxException {
		StringBuffer buffer = new StringBuffer();
		String line = null;
		BufferedReader reader = request.getReader();
		while((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		
		JsonObject data = JsonParser.parseString(buffer.toString()).getAsJsonObject();
		return data;
	}
	
	/*
	 * Check une partie des parametre JSON
	 * Prend en arguments
	 * String param_nam : nom du paramètre dans l'objet JSON
	 * JsonObject data : objet JSON que l'on veut analyser
	 * boolean isRequired : si oui ou non le paramètre est requis pour la requete
	 * boolean isNotNullable : si oui ou non le paramètre ne peut pas valoir null dans la BDD (true si NOT NULL et false si non)
	 */
	public static HashMap<String,String> checkParameter(String param_name, JsonObject data, boolean isRequired, boolean isNotNullable){
		String parameter = null;
		String error = "";
		
		//Check si le parametre existe dans le JSON
		if(data.has(param_name)) {
			//recupère la valeur stockée dans le JSON
			//Si la valeur n'est pas nulle, la stocket sous forme de String sinon stocker null
			parameter = data.get(param_name).isJsonNull() ? null : data.get(param_name).getAsString() ;

			if(parameter != null) {
				//Si la valeur n'est pas nulle, enlever les espaces inutiles
				parameter = parameter.trim();
			} else if(isNotNullable){
				//Si la valeur est nulle et que elle ne peut pas valoir null dans la BDD renvoyer un message d'erreur
				error += "The parameter "+param_name+" cannot be null\n";
			}
		}else if(isRequired) { 
			//Si le param n'existe pas, check s'il est nécessaire pour la requete
			//Si oui, mettre un message d'erreur
			error += "The parameter "+param_name+" is missing\n";
			if(!isNotNullable) //Check si le param peut valoir null dans la BDD
				error += "(The parameter "+param_name+" can be null but the null value should be precised in the request)\n";
		}
		// else si le param n'existe pas et n'est pas requis ne rien faire
		
		HashMap<String,String> results = new HashMap<String,String>();
		results.put("parameter", parameter);
		results.put("error", error);

		return results;
	}
}
