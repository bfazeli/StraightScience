package sbhacksiii.bfazeli.straightscience;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by bijanfazeli on 1/22/17.
 */

public class ReadRSS extends AsyncTask<Void, Void, Void> {

    Context context;
    String address = "http://feeds.abcnews.com/abcnews/travelheadlines";
    ProgressDialog progressDialog;
    URL url;
    public ReadRSS(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        ProcessXml (Getdata());

        return null;
    }

    private void ProcessXml(Document data) {
        if (data != null) {
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();
            Node currentChild, current;
            NodeList itemChilds;
            for (int i = 0; i < items.getLength(); ++i) {
                currentChild = items.item(i);
                if (currentChild.getNodeName().equalsIgnoreCase("item")) {
                    itemChilds = currentChild.getChildNodes();
                    for (int j=0; j < itemChilds.getLength(); ++j) {
                        current = itemChilds.item(j);
                        Log.d("textcontent", current.getTextContent());
                        Log.i("break", "Empty line");
                    }
                }
            }
        }
    }

    public Document Getdata() {
        try {
            url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDoc = builder.parse(inputStream);
            return xmlDoc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
