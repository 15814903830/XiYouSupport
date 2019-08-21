package com.chengfan.xiyou.common;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-03/11:02
 * @Description: api
 */
public class APIContents {
    private static final APIContents ourInstance = new APIContents();

    public static APIContents getInstance() {
        return ourInstance;
    }

    private APIContents() {
    }

    public static String HOST = "http://api.maihui111.com";
    /*登录*/
    public static String LOGIN = HOST + "/api/Account/Login";
    /*注册*/
    public static String REGISTER = HOST + "/api/Account/Register";
    /*发送验证码*/
    public static String SEND_CODE = HOST + "/api/Common/SendCode";
    /*找回密码*/
    public static String FIND = HOST + "/api/Account/FindPassword";
    /*完善信息*/
    public static String UPDATE = HOST + "/api/Account/Update";
    /*上传文件*/
    public static String UPLOAD_FILE = HOST + "/api/Upload/UploadFile";

    /*上传多个文件*/
    public static String UPLOAD_FILE_MORE = HOST + "/api/Upload/UploadMore";
    /*通讯录*/
    public static String ADDRESS_BOOK = HOST + "/api/Member/AddressBook";
    /*删除好友*/
    public static String REMOVE_FRIEND = HOST + "/api/Member/RemoveAddressBook";
    /*群组*/
    public static String GROUP = HOST + "/api/Member/TeamList/";
    /*解散群*/
    public static String REMOVE_TEAM = HOST + "/api/Member/RemoveTeam";
    /*退出群*/
    public static String REOMT_TEAM_MEMBER = HOST + "/api/Member/RemoveTeamMember";
    /*创建群组*/
    public static String CREATE_TEAM = HOST + "/api/Member/CreateTeam";
    /*群详情*/
    public static String TEAM_DETAIL = HOST + "/api/Member/TeamDetail/";
    /*设置勿扰*/
    public static String TEAM_MEMBER = HOST + "/api/Member/TeamMember";
    /*修改群名称*/
    public static String UPDATE_TEAM = HOST + "/api/Member/UpdateTeam";
    /*首页*/
    public static String HOME_PAGE = HOST + "/api/Member/HomePage";
    /*首页加载更多*/
    public static String HOME_PAGE_MORE = HOST + "/api/Member/HomePageMore";
    /*游戏陪玩*/
    public static String PLAY_LIST = HOST + "/api/AccompanyPlay/List";
    /*个人详情*/
    public static String ACCOMPANY = HOST + "/api/Member/MemberHomePage";
    /*关注、取消关注*/
    public static String MEMBER_SHIP = HOST + "/api/Account/MemberShip";
    public static String MEMBER_LIKE = HOST + "/api/MemberNews/Praise";
    /*陪玩详情*/
    public static String ACCOMPANY_DETAIL = HOST + "/api/AccompanyPlay/Detail";
    /*检查是否能发私信*/
    public static String CheckPrivateLetter = HOST + "/api/Member/CheckPrivateLetter";
    /*检查是否是VIP*/
    public static String CheckVIP = HOST + "/api/Member/CheckVIP/";
    /*打招呼*/
    public static String SayHi = HOST + "/api/Member/SayHi";
    /*我的关注*/
    public static String PageList = HOST + "/api/MemberNews/PageList";
    /*我的动态*/
    public static String MineList = HOST + "/api/MemberNews/List";
    /*发布动态*/
    public static String PublishMemberNews = HOST + "/api/MemberNews/PublishMemberNews";
    /*动态详情*/
    public static String DynamicDetail = HOST + "/api/MemberNews/Detail/";
    /*个人中心*/
    public static String Conter = HOST + "/api/Account/Conter";
    /*个人中心我的关注*/
    public static String FinanceRecord = HOST + "/api/Account/FinanceRecord";
    /*外貌*/
    public static String Face = "http://xy.gx11.cn/api/Common/SystemConfig/2";
    /*工作*/
    public static String Job = "http://xy.gx11.cn/api/Common/SystemConfig/3";
    /*身高*/
    public static String ShenGao = "http://xy.gx11.cn/api/Common/SystemConfig/4";
    /*体重*/
    public static String TiZhong = "http://xy.gx11.cn/api/Common/SystemConfig/5";
    public static String config= "http://xy.gx11.cn/api/Common/SystemConfigs/4%2C5%2C2";
    /*个人中心--- 编辑资料*/
    public static String GetMemberInfo = "http://xy.gx11.cn/api/Account/GetMemberInfo";
    /*陪玩类型*/
    public static String AccompanyPlaySubject = HOST + "/api/AccompanyPlay/AccompanyPlaySubject";
    public static String Publish = HOST + "/api/AccompanyPlay/Publish";
    /*我的陪玩*/
    public static String ListByMemberId = HOST + "/api/AccompanyPlay/ListByMemberId";
    /*w我的会员*/
    public static String VipSetMeal = HOST + "/api/Common/VipSetMeal";
    /*账号信息*/
    public static String GetAccount = HOST + "/api/Account/GetAccount/";
    /*我的家族*/
    public static String MyFamily = HOST + "/api/Member/MyFamily";
    /*家族成员列表*/
    public static String FamilyMemberList = HOST + "/api/Member/FamilyMemberList";
    /*编辑家族*/
    public static String UpdateFamily = HOST + "/api/Member/UpdateFamily";
    /*创建家族*/
    public static String CreateFamily = HOST + "/api/Member/CreateFamily";
    /*打招呼*/
    public static String NearbyMember = HOST + "/api/Member/NearbyMember";
    /*搜索陪玩项目*/
    public static String Search = HOST + "/api/AccompanyPlay/Search";
    /*搜索用户*/
    public static String SearchMember = HOST + "/api/Member/SearchMember";
    /*搜索家族*/
    public static String SearchFamily = HOST + "/api/Member/SearchFamily";
    /*分类列表*/
    public static String AccompanyPlayList = HOST + "/api/AccompanyPlay/List";
    /*陪玩订单*/
    public static String CreateAccompanyPlayOrder = HOST + "/api/Order/CreateAccompanyPlayOrder";
    /*删除动态*/
    public static String RemoveNews = HOST + "/api/MemberNews/RemoveNews";
    /*letter*/
    public static String SendPrivateLetter = HOST + "/Api/Member/SendPrivateLetter/";
    public static String SendLIKEr = HOST + "/api/MemberNews/Praise";

    /*VIP order*/
    public static String CreateVIPOrder = HOST + "/api/Order/CreateVIPOrder";

    /*我创建的订单*/
    public static String MyCreateOrder = HOST + "/api/AccompanyPlay/MyCreateOrder";
    /*我的接单*/
    public static String MyAccompanyPlayOrder = HOST + "/api/AccompanyPlay/MyAccompanyPlayOrder";
    /*修改订单状态*/
    public static String UpdateAccompanyPlayOrderStatus = HOST + "/api/AccompanyPlay/UpdateAccompanyPlayOrderStatus";

    /*rong*/
    public static String token = "http://api-cn.ronghub.com/user/getToken.json";
    /*评论*/
    public static String PublishMemberNewsComment = HOST + "/api/MemberNews/PublishMemberNewsComment";
    public static String AddressBookByTeamId = HOST + "/api/Member/AddressBookByTeamId";
    public static String AddTeamMemberMore = HOST + "/api/Member/AddTeamMemberMore";

    public static String AppManage = HOST + "http://xy.gx11.cn/api/Common/AppManage";
}
