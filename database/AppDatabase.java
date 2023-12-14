package com.example.unlimited_store.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.unlimited_store.activities.MainActivity;

public class AppDatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 11;
    private static final String PREF_NAME = "login_pref";
    private static final String LOGIN_KEY = "login_state";
    private static SharedPreferences sharedPreferences;
    private static final String USERNAME_KEY = "username";
    private static final String WELCOME_KEY = "welcome";

    public AppDatabase(Context context) {
        super(context, "UNLIMITED_DATABASE", null, DATABASE_VERSION);
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Phương thức ghi trạng thái login vào sharedPreferences
     */
    public static void setLoginState(boolean isLoggedIn) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(LOGIN_KEY, isLoggedIn);
        editor.apply();
    }
    /**
     * Phương thức lấy trạng thái login từ sharedPreferences
     */
    public static boolean getLoginState() {
        return sharedPreferences.getBoolean(LOGIN_KEY, false);
    }
    /**
     * Phương thức ghi trạng thái username vào sharedPreferences
     */
    public static void saveUsername(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME_KEY, username);
        editor.apply();
    }
    /**
     * Phương thức lấy username từ sharedPreferences
     */
    public static String getUsername() {
        return sharedPreferences.getString(USERNAME_KEY, "");
    }
    /**
     * Phương thức ghi trạng thái welcome vào sharedPreferences
     */
    public static void setWelcomeState(boolean welcome) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(WELCOME_KEY, welcome);
        editor.apply();
    }
    /**
     * Phương thức lấy welcomeState từ sharedPreferences
     */
    public static boolean getWelcomeState() {
        return sharedPreferences.getBoolean(WELCOME_KEY, false);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * Bảng USERS
         */
        String tableUsers = "create table USERS(" +
                "role integer," +
                "email text," +
                "phoneNumber text," +
                "address text," +
                "username text primary key," +
                "password text not null)";
        db.execSQL(tableUsers);
        db.execSQL("insert into USERS values" +
                "(1,'quynh123@gmail.com','0123456789', 'Nguyễn Văn Qúa','Nhu Quynh','Quynh@123')," +
                "(1,'huyen123@gmail.com','0123456789', 'Nguyễn Văn Qúa','Thu Huyen', 'Huyen@123')," +
                "(0,'toan123@gmail.com','0123456789', 'Nguyễn Văn Qúa','Kim Toan', 'Toan@123')," +
                "(0,'ngan123@gmail.com','0123456789', 'Nguyễn Văn Qúa','Thu Ngan', 'Ngan@123');");

        /**
         * Bảng PRODUCTS
         */
        String tableProducts = "create table PRODUCTS(" +
                "idProduct integer primary key autoincrement," +
                "image text," +
                "name text," +
                "price integer," +
                "description text," +
                "type text)";
        db.execSQL(tableProducts);
        db.execSQL("insert into PRODUCTS values" +
                "(1, 'https://vhealthcoffee.com.vn/uploads/images/cafe-americano.jpg', 'Americano', 41000, 'đây là loại cà phê truyền thống của người Mỹ với vị đắng nhẹ hơn bằng cách pha loãng cà phê espresso với nước sôi theo một tỷ lệ nhất định.', 'Coffee')," +
                "(2, 'https://mediamart.vn/images/uploads/data-2022/ghj-t07H8J.jpg', 'Latte', 39000, 'Latte là một thức uống có nguồn gốc từ Ý bao gồm các nguyên liệu chính là cà phê và sữa được đánh lên, đồ uống này được tiêu thụ thường xuyên cả ở nhà và tại các quán cà phê, quán bar.', 'Coffee')," +
                "(3, 'https://winci.com.vn/wp-content/uploads/2023/11/perfect-shot-of-espresso-777x560-1.jpg', 'Espresso', 26500, 'Espresso  là một phương pháp rang và pha chế cà phê có nguồn gốc từ Ý,trong đó một lượng nhỏ nước sôi gần như bị đè nén dưới áp lực (ép) qua hạt cà phê nghiền. Espresso cũng là nền tảng cho nhiều loại đồ uống từ cà phê khác như cà phê latte, cappuccino,...', 'Coffee')," +
                "(4, 'https://mediamart.vn/images/uploads/data-2022/ghj-c1x3LL.jpg', 'Cappuccino', 25000, 'Capuchino là một loại cà phê gồm có cà phê và lớp bọt sữa, cùng với một ít bột ca cao hoặc bột quế được rắc lên bề mặt thức uống trước khi phục vụ.', 'Coffee')," +
                "(5, 'https://mediamart.vn/images/uploads/data-2022/ghj-BCe85n.jpg', 'Mocha', 37000, 'Cà phê Mocha Giống với tách cà phê latte, cà phê mocha dựa trên espresso và sữa nóng nhưng có thêm hương vị sô cô la và chất làm ngọt, thường ở dạng bột ca cao và đường. Nhiều loại sử dụng siro sô cô la thay vào đó và một số có thể chứa sô cô la đen hoặc sô cô la sữa.', 'Coffee')," +
                "(6, 'https://mediamart.vn/images/uploads/data-2022/ghj-BOsK1B.jpg', 'Black Coffee', 15000, ' Café truyền thống là cafe rang xay nguyên chất theo phong cách café Việt Nam, pha chế kiểu phin', 'Coffee')," +
                "(7, 'https://mediamart.vn/images/uploads/data-2022/ghj-x1KMG4.jpg', 'Latte Macchiato', 51000, 'Latte Macchiato  là một loại đồ uống nóng rất được ưa chuộng. Thành phần của nó gồm có cà phê espresso và sữa. Về cơ bản thì latte macchiatio giống như cà phê sữa, nhưng lượng sữa nhiều hơn. Ở Ý ban đầu loại cà phê này được làm riêng cho trẻ em để chúng cũng được uống \"cà phê\" như người lớn, vì thế mà lượng caffein trong latte macchiato rất ít.', 'Coffee')," +
                "(8, 'https://mediamart.vn/images/uploads/data-2022/ghj-1QhcB3.jpg', 'Cappuccino XL', 29000, 'Capuchino là một loại cà phê gồm có cà phê và lớp bọt sữa, cùng với một ít bột ca cao hoặc bột quế được rắc lên bề mặt thức uống trước khi phục vụ.', 'Coffee')," +
                "(9, 'https://domf5oio6qrcr.cloudfront.net/medialibrary/6320/conversions/9bf65630-37e8-4802-978a-df3b1933851e-thumb.jpg', 'Green Tea', 15000, 'Chứa caffeine rất tốt để uống vào buổi sáng giúp tỉnh táo, một lựa chọn khác thay vì uống cà phê.', 'Tea')," +
                "(10, 'https://recipes.net/wp-content/uploads/2021/01/what-is-chamomile-tea-scaled.jpg', 'Herbal Tea', 30000, 'Trà thảo dược giúp nâng cao sức khỏe với liều lượng phù hợp.', 'Tea')," +
                "(11, 'https://www.hoteljob.vn/files/Anh-HTJ-Hong/cac-loai-tra-duoc-ua-chuong-nhat-va-cong-dung-cu-the-3.jpg', 'Fruit Tea', 27000, 'Chứa rất nhiều vitamin và khoáng chất, không có caffeine và các hoạt chất như trà thảo dược', 'Tea')," +
                "(12, 'https://www.hoteljob.vn/files/Anh-HTJ-Hong/cac-loai-tra-duoc-ua-chuong-nhat-va-cong-dung-cu-the-4.jpg', 'Peppermin Tea', 21000, 'Không có caffeine nên bạn có thể uống khi nào thích.', 'Tea')," +
                "(13, 'https://coffeeaffection.com/wp-content/uploads/2022/10/decaf-tea_Julia-Sakelli_Pexels.jpg', 'Decaf Tea', 32000, 'Nguyên liệu có lợi cho sức khỏe như quả tầm xuân giàu vitamin C, hoa phù dung giảm mệt mỏi', 'Tea')," +
                "(14, 'https://media.glide.mailplus.co.uk/prod/images/950_633/gm-c2727c9e-c9f0-48aa-aa0c-9f90c637569d-comp-01-151.jpg', 'Loose Leaf Tea', 29000, 'Trà lá được khuyên dùng hơn trà túi lọc vì chất lượng hơn bởi đa phần', 'Tea')," +
                "(15, 'https://www.hoteljob.vn/files/Anh-HTJ-Hong/cac-loai-tra-duoc-ua-chuong-nhat-va-cong-dung-cu-the-5.jpg', 'Rose Tea', 34000, 'Trà chứa lượng kali cao giúp cân bằng độ pH của máu, làm se và cầm máu', 'Tea')," +
                "(16, 'https://www.hoteljob.vn/files/Anh-HTJ-Hong/cac-loai-tra-duoc-ua-chuong-nhat-va-cong-dung-cu-the-6.jpg', 'Lavender Tea', 45000, 'Giúp điều trị rối loạn tiêu hóa có nguồn gốc từ thần kinh - kích thích sự thèm ăn', 'Tea')," +
                "(17, 'https://douongcaocap.vn/wp-content/uploads/2019/10/Bia-Super-Bock-Mini-5.2-%E2%80%93-Chai-250ml-%E2%80%93-Thung-24-Chai-2.jpg', 'Bock', 6900000, 'Bock là một loại bia của Đức có tính chất mạnh hơn loại lager thông thường và có màu tối.', 'Beer')," +
                "(18, 'https://i.pinimg.com/564x/d0/5b/77/d05b77859f1274473b87bf940849e8fd.jpg', 'Dunkel', 3020000, 'Dunkel cũng là một loại bia có nguồn gốc từ nước Đức, có màu hồ phách đạm và mạnh vừa phải.', 'Beer')," +
                "(19, 'https://bianhapkhau.com.vn/uploads/bca7c7a4c3b9856d70e3f00ce854bf2c.jpg', 'Pale Lager', 1005000, 'Pale Lager là loại phổ biến nhất, có ga mạnh nhưng đồ còn lại không cao.', 'Beer')," +
                "(20, 'https://douongnhapkhau.com/wp-content/uploads/2020/01/Bia-Pilsner-Urquell-lon-330-ml.jpg', 'Pilsner', 3240000, 'Pilsner là một trong những loại pale lager và là loại bia trẻ nhất trên thế giới.', 'Beer')," +
                "(21, 'https://product.hstatic.net/1000282430/product/beer--pale-ale-east-west-330ml_19eff1df2d6540818bccfd012117c274.jpg', 'Pale Ale', 4505000, 'Pale Ale rất phổ biến trên thế giới. Loại bia này được phát minh ra vào thời kỳ kỹ thuật ủ bia được cải tạo đáng kể.', 'Beer')," +
                "(22, 'https://douongcaocap.vn/wp-content/uploads/2019/07/Bia-Porter-8-20-8.5-Chai-500ml-Th%C3%B9ng-20-Chai-1.jpg', 'Porter', 1200000, 'Porter có màu rất tối đậm, gần như đen do được làm từ lúa mạch được rang rất kỳ và trải qua quá trình lên men.', 'Beer')," +
                "(23, 'https://i.pinimg.com/564x/87/3e/3d/873e3d7b901b3fbb8cb223637f8c5869.jpg', 'Stout', 3240000, 'Porter có màu rất tối đậm, gần như đen do được làm từ lúa mạch được rang rất kỳ và trải qua quá trình lên men.', 'Beer')," +
                "(24, 'https://media.post.rvohealth.io/wp-content/uploads/2020/08/orange-juice-732x549-thumbnail.jpg', 'Orange Juice', 14000, 'Hương vị đậm đà từ quả cam tươi.', 'Juice')," +
                "(25, 'https://www.indianhealthyrecipes.com/wp-content/uploads/2022/02/apple-juice-recipe.jpg', 'Apple Juice', 20000, 'Vị chua nhẹ với giống táo Mỹ.', 'Juice')," +
                "(26, 'https://img.onmanorama.com/content/dam/mm/en/food/recipe/images/2023/4/20/banana-juice.jpg', 'Banana Juice', 29000, 'Nước ép 100% từ quả chuối Nam Phi', 'Juice')," +
                "(27, 'https://cdn.healthyrecipes101.com/recipes/images/juices/is-concord-grape-juice-healthy-clakvj5ee008kpw1b3qbf3iys.webp', 'Grape Juice', 31000, 'Nước ép từ nho Bình Thuận, vị chua nhẹ thanh mát.', 'Juice')," +
                "(28, 'https://insanelygoodrecipes.com/wp-content/uploads/2021/09/Refreshing-Starfruit-Juice-683x1024.jpg', 'Starfruit Juice', 16000, '100% nước ép từ quả khế tươi', 'Juice')," +
                "(29, 'https://sainthonore.com.vn/media/product/2023/05//mango-frooti.jpg', 'Mango Juice', 23000, 'Thơm mát, ngọt dịu cho tâm trạng của bạn.', 'Juice')," +
                "(30, 'https://4.imimg.com/data4/HR/VP/MY-30165244/lemon-juice.jpg', 'Lemon Juice', 19000, 'Tươi mát cho ngày hè nóng nực của bạn', 'Juice')");

        /**
         * Bảng CART
         */
        String tableCart = "create table CART(" +
                "idCart integer primary key autoincrement," +
                "quantity integer," +
                "state integer," +
                "total integer," +
                "topping integer," +
                "extraCream integer," +
                "idProduct integer," +
                "username text," +
                "foreign key (idProduct) references PRODUCTS(idProduct)," +
                "foreign key (username) references USERS(username))";
        db.execSQL(tableCart);

        /**
         * Bảng HISTORY
         */
        String tableHistory = "create table HISTORY(" +
                "idHistory integer primary key autoincrement," +
                "quantity integer," +
                "state integer," +
                "total integer," +
                "topping integer," +
                "extraCream integer," +
                "username text," +
                "idProduct integer," +
                "foreign key (username) references USERS(username),"+
                "foreign key (idProduct) references PRODUCTS(idProduct))";
        db.execSQL(tableHistory);

        /**
         * Bảng FEEDBACK
         */
        String tableFeedabck = "create table FEEDBACK(" +
                "idFeedback integer primary key autoincrement," +
                "content text," +
                "username text,"+
                "foreign key (username) references USERS(username))";
        db.execSQL(tableFeedabck);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("drop table if exists USERS");
            db.execSQL("drop table if exists PRODUCTS");
            db.execSQL("drop table if exists CART");
            db.execSQL("drop table if exists HISTORY");
            db.execSQL("drop table if exists FEEDBACK");
            onCreate(db);
        }
    }


    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
}
