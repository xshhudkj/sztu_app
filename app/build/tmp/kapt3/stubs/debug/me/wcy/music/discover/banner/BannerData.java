package me.wcy.music.discover.banner;

import com.google.gson.annotations.SerializedName;
import me.wcy.music.common.bean.SongData;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b3\b\u0086\b\u0018\u00002\u00020\u0001B\u00a7\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0014\u001a\u00020\f\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0017J\t\u0010-\u001a\u00020\u0003H\u00c6\u0003J\t\u0010.\u001a\u00020\u0003H\u00c6\u0003J\t\u0010/\u001a\u00020\u0003H\u00c6\u0003J\t\u00100\u001a\u00020\u0003H\u00c6\u0003J\t\u00101\u001a\u00020\u0003H\u00c6\u0003J\t\u00102\u001a\u00020\fH\u00c6\u0003J\t\u00103\u001a\u00020\u0003H\u00c6\u0003J\t\u00104\u001a\u00020\u0003H\u00c6\u0003J\t\u00105\u001a\u00020\u0005H\u00c6\u0003J\t\u00106\u001a\u00020\u0007H\u00c6\u0003J\t\u00107\u001a\u00020\u0003H\u00c6\u0003J\t\u00108\u001a\u00020\u0003H\u00c6\u0003J\t\u00109\u001a\u00020\u0003H\u00c6\u0003J\t\u0010:\u001a\u00020\fH\u00c6\u0003J\t\u0010;\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010<\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003J\u00ab\u0001\u0010=\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u00032\b\b\u0002\u0010\u0011\u001a\u00020\u00032\b\b\u0002\u0010\u0012\u001a\u00020\u00032\b\b\u0002\u0010\u0013\u001a\u00020\u00032\b\b\u0002\u0010\u0014\u001a\u00020\f2\b\b\u0002\u0010\u0015\u001a\u00020\u00032\b\b\u0002\u0010\u0016\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010>\u001a\u00020\f2\b\u0010?\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010@\u001a\u00020\u0007H\u00d6\u0001J\t\u0010A\u001a\u00020\u0003H\u00d6\u0001R\u0016\u0010\u0011\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0016\u0010\u0016\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0019R\u0016\u0010\u0010\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0019R\u0016\u0010\r\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0019R\u0016\u0010\u000b\u001a\u00020\f8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0019R\u0016\u0010\u0013\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0019R\u0016\u0010\u0015\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0019R\u0016\u0010\u0012\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0019R\u0016\u0010\u0014\u001a\u00020\f8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001eR\u0018\u0010\u000e\u001a\u0004\u0018\u00010\u000f8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\'R\u0016\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u0016\u0010\b\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\u0019R\u0016\u0010\t\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u0019R\u0016\u0010\n\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\u0019\u00a8\u0006B"}, d2 = {"Lme/wcy/music/discover/banner/BannerData;", "", "pic", "", "targetId", "", "targetType", "", "titleColor", "typeTitle", "url", "exclusive", "", "encodeId", "song", "Lme/wcy/music/common/bean/SongData;", "bannerId", "alg", "scm", "requestId", "showAdTag", "sCtrp", "bannerBizType", "(Ljava/lang/String;JILjava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLjava/lang/String;Lme/wcy/music/common/bean/SongData;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLjava/lang/String;Ljava/lang/String;)V", "getAlg", "()Ljava/lang/String;", "getBannerBizType", "getBannerId", "getEncodeId", "getExclusive", "()Z", "getPic", "getRequestId", "getSCtrp", "getScm", "getShowAdTag", "getSong", "()Lme/wcy/music/common/bean/SongData;", "getTargetId", "()J", "getTargetType", "()I", "getTitleColor", "getTypeTitle", "getUrl", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class BannerData {
    @com.google.gson.annotations.SerializedName(value = "pic")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String pic = null;
    @com.google.gson.annotations.SerializedName(value = "targetId")
    private final long targetId = 0L;
    @com.google.gson.annotations.SerializedName(value = "targetType")
    private final int targetType = 0;
    @com.google.gson.annotations.SerializedName(value = "titleColor")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String titleColor = null;
    @com.google.gson.annotations.SerializedName(value = "typeTitle")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String typeTitle = null;
    @com.google.gson.annotations.SerializedName(value = "url")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String url = null;
    @com.google.gson.annotations.SerializedName(value = "exclusive")
    private final boolean exclusive = false;
    @com.google.gson.annotations.SerializedName(value = "encodeId")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String encodeId = null;
    @com.google.gson.annotations.SerializedName(value = "song")
    @org.jetbrains.annotations.Nullable()
    private final me.wcy.music.common.bean.SongData song = null;
    @com.google.gson.annotations.SerializedName(value = "bannerId")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String bannerId = null;
    @com.google.gson.annotations.SerializedName(value = "alg")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String alg = null;
    @com.google.gson.annotations.SerializedName(value = "scm")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String scm = null;
    @com.google.gson.annotations.SerializedName(value = "requestId")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String requestId = null;
    @com.google.gson.annotations.SerializedName(value = "showAdTag")
    private final boolean showAdTag = false;
    @com.google.gson.annotations.SerializedName(value = "s_ctrp")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String sCtrp = null;
    @com.google.gson.annotations.SerializedName(value = "bannerBizType")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String bannerBizType = null;
    
    public BannerData(@org.jetbrains.annotations.NotNull()
    java.lang.String pic, long targetId, int targetType, @org.jetbrains.annotations.NotNull()
    java.lang.String titleColor, @org.jetbrains.annotations.NotNull()
    java.lang.String typeTitle, @org.jetbrains.annotations.NotNull()
    java.lang.String url, boolean exclusive, @org.jetbrains.annotations.NotNull()
    java.lang.String encodeId, @org.jetbrains.annotations.Nullable()
    me.wcy.music.common.bean.SongData song, @org.jetbrains.annotations.NotNull()
    java.lang.String bannerId, @org.jetbrains.annotations.NotNull()
    java.lang.String alg, @org.jetbrains.annotations.NotNull()
    java.lang.String scm, @org.jetbrains.annotations.NotNull()
    java.lang.String requestId, boolean showAdTag, @org.jetbrains.annotations.NotNull()
    java.lang.String sCtrp, @org.jetbrains.annotations.NotNull()
    java.lang.String bannerBizType) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPic() {
        return null;
    }
    
    public final long getTargetId() {
        return 0L;
    }
    
    public final int getTargetType() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTitleColor() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTypeTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUrl() {
        return null;
    }
    
    public final boolean getExclusive() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEncodeId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final me.wcy.music.common.bean.SongData getSong() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getBannerId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAlg() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getScm() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRequestId() {
        return null;
    }
    
    public final boolean getShowAdTag() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSCtrp() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getBannerBizType() {
        return null;
    }
    
    public BannerData() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component13() {
        return null;
    }
    
    public final boolean component14() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component15() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component16() {
        return null;
    }
    
    public final long component2() {
        return 0L;
    }
    
    public final int component3() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    public final boolean component7() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final me.wcy.music.common.bean.SongData component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.discover.banner.BannerData copy(@org.jetbrains.annotations.NotNull()
    java.lang.String pic, long targetId, int targetType, @org.jetbrains.annotations.NotNull()
    java.lang.String titleColor, @org.jetbrains.annotations.NotNull()
    java.lang.String typeTitle, @org.jetbrains.annotations.NotNull()
    java.lang.String url, boolean exclusive, @org.jetbrains.annotations.NotNull()
    java.lang.String encodeId, @org.jetbrains.annotations.Nullable()
    me.wcy.music.common.bean.SongData song, @org.jetbrains.annotations.NotNull()
    java.lang.String bannerId, @org.jetbrains.annotations.NotNull()
    java.lang.String alg, @org.jetbrains.annotations.NotNull()
    java.lang.String scm, @org.jetbrains.annotations.NotNull()
    java.lang.String requestId, boolean showAdTag, @org.jetbrains.annotations.NotNull()
    java.lang.String sCtrp, @org.jetbrains.annotations.NotNull()
    java.lang.String bannerBizType) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}