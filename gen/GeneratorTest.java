package gen;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/16/14
 * Time: 4:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class GeneratorTest {

    public static void simpleTest () throws Exception {

        App app = new App();
        app.setTechnologyStack(TechnologyStack.rails);
        app.setRootDir("C:/tmp");
        app.setName("testing");

        AppConfig appConfig = new AppConfig();
        appConfig.setColor1("blue");
        appConfig.setColor2("white");
        appConfig.setColor3("orange");
        appConfig.setSearch(true);

        Model item = Model.parseModel("item: name MAX(255)");
        Model person = Model.parseModel("person: name string MIN(1), age int MIN(1) MAX(130), dob date REQUIRED, money float");
        person.hasMany(item);

        app.setAppConfig(appConfig);
        app.addTopLevelModel(person);
        app.setFrontPageListModel(item);
        app.setFrontPageSearchModel(person);

        Generator.createAndGen(app, true);
        /**
         * End result: simple CRUD app where a person can manage a list of possessions
         */
    }

    public static void main (String [] args) throws Exception {
    	boolean windows = true;
        salesTest(windows);
    }

    public static void salesTest (boolean windows) throws Exception {

        App app = new App();
        app.setWindows(windows);
        app.setTechnologyStack(TechnologyStack.rails);
        app.setRootDir(windows ? "C:/Users/jsanchez/Downloads/apps" : "/Users/jeffreysanchez/rails_projects/generated");
        app.setName("sales");
        app.setTagLine("Sell Sell Sell");
        app.addPlaceholderPages(new String[] {"about", "help", "news", "contact"});
        app.addStaticMenuItems(new String[] {"about", "news", "help"});

        AppConfig appConfig = new AppConfig();
        appConfig.setColor1("blue");
        appConfig.setColor2("white");
        appConfig.setColor3("green");
        appConfig.setSearch(true);
        appConfig.setNeedsAuth(true);

        Model [] models = Model.parseModels(new String[] {
                "user: username string, email, password, premium boolean, avatar image, has_many contact, has_one company, has_many opportunity",
                "company: name, address, phone, billing_address address, mailing_address address, description string, has_many contact",
                "opportunity: name, address, phone, has_one company, website url, value currency, case_number integer, description long_string, priority fixed_list(low|medium|high)",
                "contact: first_name, last_name, photo list(image), email short_string REQUIRED, home phone, cell phone, sex fixed_list(male|female), contact_type fixed_list(personal|sales|presales)"
        });

        app.setModels(models);
        app.setAppConfig(appConfig);
        app.setTopLevelModels("user", "contact");
        app.setFrontPageListModel(models [1]);   // TODO more realistic example of good front page list content

        Generator.createAndGen(app, true);
        /**
         * End result: simple CRUD app with the following pages:
         * splash intro page with login / signup and footer with about us, etc. pages
         * sign up page
         * log in page
         * logged in page goes to: current user's contacts list, with edit/view/delete options, as well as create new
         * contact details page shows company link / details?
         *
         * Menu options should include sections for contacts and companies? How to configure?
         * Layout is???
         * What's in sidebar?
         */
    }

    public static App initialApp (String name, String tagline) throws Exception {

        boolean windows = false;
        App app = new App();
        app.setWindows(windows);
        app.setTechnologyStack(TechnologyStack.rails);
        app.setRootDir(windows ? "C:/Users/jsanchez/Downloads/apps" : "/Users/jeffreysanchez/rails_projects/generated");
        app.setName(name);
        app.setTagLine(tagline);
        app.addPlaceholderPages(new String[] {"about", "help"});
        app.addStaticMenuItems(new String[] {"about"});

        AppConfig appConfig = app.getAppConfig();
        appConfig.setColor1("blue");
        appConfig.setColor2("white");
        appConfig.setColor3("green");
        //appConfig.setSearch(true);
        //appConfig.setNeedsAuth(true);
        return (app);
    }

    public static void flylineSite () throws Exception {
        App app = initialApp("flyline", "Activity in the New England Paragliding Community");
        Model postModel = Model.parseModel("post: name required, email required, content long_string required, site fixed_list(mt_tom|plymouth|mt_washington|welfleet)");

        app.setModels(new Model[] {postModel});
        app.getAppConfig().setColor1("blue");
        app.getAppConfig().setColor2("white");
        app.setJumbotronImageUrl("http://flickr.com/something/MtTom.jpg");

        //app.setTopLevelModels("post"); -- should be unnecessary if there's only 1 model
        app.setFrontPageListModel(postModel);
        Generator.createAndGen(app, true);
    }
}
