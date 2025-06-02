package me.wcy.music.common.bean;

import com.google.gson.annotations.SerializedName;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\bh\b\u0086\b\u0018\u00002\u00020\u0001B\u0099\u0003\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\b\b\u0002\u0010\f\u001a\u00020\u0007\u0012\b\b\u0002\u0010\r\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0013\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0016\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0016\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0016\u0012\b\b\u0002\u0010\u0019\u001a\u00020\u0016\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u0016\u0012\b\b\u0002\u0010\u001b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u001c\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u001d\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u001e\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u001f\u001a\u00020\u0007\u0012\b\b\u0002\u0010 \u001a\u00020\u0007\u0012\b\b\u0002\u0010!\u001a\u00020\u0007\u0012\b\b\u0002\u0010\"\u001a\u00020\u0007\u0012\n\b\u0002\u0010#\u001a\u0004\u0018\u00010$\u0012\b\b\u0002\u0010%\u001a\u00020&\u0012\b\b\u0002\u0010\'\u001a\u00020\u0007\u0012\b\b\u0002\u0010(\u001a\u00020\u0007\u0012\b\b\u0002\u0010)\u001a\u00020\u0007\u0012\b\b\u0002\u0010*\u001a\u00020\u0007\u0012\b\b\u0002\u0010+\u001a\u00020\u0007\u0012\b\b\u0002\u0010,\u001a\u00020\u0007\u0012\b\b\u0002\u0010-\u001a\u00020\u0007\u0012\b\b\u0002\u0010.\u001a\u00020\u0005\u0012\u000e\b\u0002\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00050\n\u0012\b\b\u0002\u00100\u001a\u00020\u0005\u0012\b\b\u0002\u00101\u001a\u00020\u0005\u00a2\u0006\u0002\u00102J\t\u0010b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010c\u001a\u00020\u0007H\u00c6\u0003J\t\u0010d\u001a\u00020\u0005H\u00c6\u0003J\t\u0010e\u001a\u00020\u0013H\u00c6\u0003J\t\u0010f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010g\u001a\u00020\u0016H\u00c6\u0003J\t\u0010h\u001a\u00020\u0016H\u00c6\u0003J\t\u0010i\u001a\u00020\u0016H\u00c6\u0003J\t\u0010j\u001a\u00020\u0016H\u00c6\u0003J\t\u0010k\u001a\u00020\u0016H\u00c6\u0003J\t\u0010l\u001a\u00020\u0005H\u00c6\u0003J\t\u0010m\u001a\u00020\u0005H\u00c6\u0003J\t\u0010n\u001a\u00020\u0007H\u00c6\u0003J\t\u0010o\u001a\u00020\u0007H\u00c6\u0003J\t\u0010p\u001a\u00020\u0007H\u00c6\u0003J\t\u0010q\u001a\u00020\u0007H\u00c6\u0003J\t\u0010r\u001a\u00020\u0007H\u00c6\u0003J\t\u0010s\u001a\u00020\u0007H\u00c6\u0003J\t\u0010t\u001a\u00020\u0007H\u00c6\u0003J\u000b\u0010u\u001a\u0004\u0018\u00010$H\u00c6\u0003J\t\u0010v\u001a\u00020&H\u00c6\u0003J\t\u0010w\u001a\u00020\u0007H\u00c6\u0003J\t\u0010x\u001a\u00020\u0007H\u00c6\u0003J\t\u0010y\u001a\u00020\u0007H\u00c6\u0003J\t\u0010z\u001a\u00020\u0007H\u00c6\u0003J\t\u0010{\u001a\u00020\u0007H\u00c6\u0003J\t\u0010|\u001a\u00020\u0007H\u00c6\u0003J\t\u0010}\u001a\u00020\u0007H\u00c6\u0003J\t\u0010~\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u007f\u001a\u00020\u0005H\u00c6\u0003J\u0010\u0010\u0080\u0001\u001a\b\u0012\u0004\u0012\u00020\u00050\nH\u00c6\u0003J\n\u0010\u0081\u0001\u001a\u00020\u0005H\u00c6\u0003J\n\u0010\u0082\u0001\u001a\u00020\u0005H\u00c6\u0003J\n\u0010\u0083\u0001\u001a\u00020\u0007H\u00c6\u0003J\u0010\u0010\u0084\u0001\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u00c6\u0003J\n\u0010\u0085\u0001\u001a\u00020\u0007H\u00c6\u0003J\n\u0010\u0086\u0001\u001a\u00020\u0007H\u00c6\u0003J\n\u0010\u0087\u0001\u001a\u00020\u0005H\u00c6\u0003J\n\u0010\u0088\u0001\u001a\u00020\u0007H\u00c6\u0003J\u009e\u0003\u0010\u0089\u0001\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\b\b\u0002\u0010\f\u001a\u00020\u00072\b\b\u0002\u0010\r\u001a\u00020\u00072\b\b\u0002\u0010\u000e\u001a\u00020\u00052\b\b\u0002\u0010\u000f\u001a\u00020\u00072\b\b\u0002\u0010\u0010\u001a\u00020\u00072\b\b\u0002\u0010\u0011\u001a\u00020\u00052\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u00032\b\b\u0002\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u00162\b\b\u0002\u0010\u0018\u001a\u00020\u00162\b\b\u0002\u0010\u0019\u001a\u00020\u00162\b\b\u0002\u0010\u001a\u001a\u00020\u00162\b\b\u0002\u0010\u001b\u001a\u00020\u00052\b\b\u0002\u0010\u001c\u001a\u00020\u00072\b\b\u0002\u0010\u001d\u001a\u00020\u00072\b\b\u0002\u0010\u001e\u001a\u00020\u00072\b\b\u0002\u0010\u001f\u001a\u00020\u00072\b\b\u0002\u0010 \u001a\u00020\u00072\b\b\u0002\u0010!\u001a\u00020\u00072\b\b\u0002\u0010\"\u001a\u00020\u00072\n\b\u0002\u0010#\u001a\u0004\u0018\u00010$2\b\b\u0002\u0010%\u001a\u00020&2\b\b\u0002\u0010\'\u001a\u00020\u00072\b\b\u0002\u0010(\u001a\u00020\u00072\b\b\u0002\u0010)\u001a\u00020\u00072\b\b\u0002\u0010*\u001a\u00020\u00072\b\b\u0002\u0010+\u001a\u00020\u00072\b\b\u0002\u0010,\u001a\u00020\u00072\b\b\u0002\u0010-\u001a\u00020\u00072\b\b\u0002\u0010.\u001a\u00020\u00052\u000e\b\u0002\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00050\n2\b\b\u0002\u00100\u001a\u00020\u00052\b\b\u0002\u00101\u001a\u00020\u0005H\u00c6\u0001J\u0015\u0010\u008a\u0001\u001a\u00020&2\t\u0010\u008b\u0001\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\n\u0010\u008c\u0001\u001a\u00020\u0007H\u00d6\u0001J\n\u0010\u008d\u0001\u001a\u00020\u0005H\u00d6\u0001R\u0016\u0010\u0012\u001a\u00020\u00138\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u00104R\u0016\u00101\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b5\u00106R\u001c\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b7\u00108R\u0016\u0010\u001b\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b9\u00106R\u0016\u0010\u0011\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b:\u00106R\u0016\u0010\u001f\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b;\u0010<R\u0016\u0010+\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b=\u0010<R\u0016\u0010\u001e\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b>\u0010<R\u0016\u0010\u0014\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b?\u0010@R\u0016\u0010\u000f\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bA\u0010<R\u0016\u0010\u001d\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bB\u0010<R\u0016\u0010\u0015\u001a\u00020\u00168\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bC\u0010DR\u0016\u0010\u001a\u001a\u00020\u00168\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bE\u0010DR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bF\u0010@R\u0016\u0010\u0018\u001a\u00020\u00168\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bG\u0010DR\u0016\u0010\u0017\u001a\u00020\u00168\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bH\u0010DR\u0016\u0010!\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bI\u0010<R\u0016\u0010*\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bJ\u0010<R\u0016\u0010,\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bK\u0010<R\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bL\u00106R\u0016\u0010\u001c\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bM\u0010<R\u0016\u0010\"\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bN\u0010<R\u0018\u0010#\u001a\u0004\u0018\u00010$8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bO\u0010PR\u0016\u0010\f\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bQ\u0010<R\u0016\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bR\u0010<R\u0016\u0010-\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bS\u0010<R\u0016\u0010.\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bT\u00106R\u0016\u00100\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bU\u00106R\u0016\u0010%\u001a\u00020&8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bV\u0010WR\u0016\u0010\u000e\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bX\u00106R\u0016\u0010)\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bY\u0010<R\u0016\u0010 \u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bZ\u0010<R\u0016\u0010(\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b[\u0010<R\u0016\u0010\u0019\u001a\u00020\u00168\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\\\u0010DR\u0016\u0010\r\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b]\u0010<R\u0016\u0010\b\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b^\u0010<R\u001c\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00050\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b_\u00108R\u0016\u0010\u0010\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b`\u0010<R\u0016\u0010\'\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\ba\u0010<\u00a8\u0006\u008e\u0001"}, d2 = {"Lme/wcy/music/common/bean/SongData;", "", "id", "", "name", "", "pst", "", "t", "ar", "", "Lme/wcy/music/common/bean/ArtistData;", "pop", "st", "rt", "fee", "v", "cf", "al", "Lme/wcy/music/common/bean/AlbumData;", "dt", "h", "Lme/wcy/music/common/bean/QualityData;", "m", "l", "sq", "hr", "cd", "no", "ftype", "djId", "copyright", "sId", "mark", "originCoverType", "originSongSimpleData", "Lme/wcy/music/common/bean/OriginSongSimpleData;", "resourceState", "", "version", "single", "rtype", "mst", "cp", "mv", "publishTime", "reason", "tns", "recommendReason", "alg", "(JLjava/lang/String;IILjava/util/List;IILjava/lang/String;IILjava/lang/String;Lme/wcy/music/common/bean/AlbumData;JLme/wcy/music/common/bean/QualityData;Lme/wcy/music/common/bean/QualityData;Lme/wcy/music/common/bean/QualityData;Lme/wcy/music/common/bean/QualityData;Lme/wcy/music/common/bean/QualityData;Ljava/lang/String;IIIIIIILme/wcy/music/common/bean/OriginSongSimpleData;ZIIIIIIILjava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;)V", "getAl", "()Lme/wcy/music/common/bean/AlbumData;", "getAlg", "()Ljava/lang/String;", "getAr", "()Ljava/util/List;", "getCd", "getCf", "getCopyright", "()I", "getCp", "getDjId", "getDt", "()J", "getFee", "getFtype", "getH", "()Lme/wcy/music/common/bean/QualityData;", "getHr", "getId", "getL", "getM", "getMark", "getMst", "getMv", "getName", "getNo", "getOriginCoverType", "getOriginSongSimpleData", "()Lme/wcy/music/common/bean/OriginSongSimpleData;", "getPop", "getPst", "getPublishTime", "getReason", "getRecommendReason", "getResourceState", "()Z", "getRt", "getRtype", "getSId", "getSingle", "getSq", "getSt", "getT", "getTns", "getV", "getVersion", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component27", "component28", "component29", "component3", "component30", "component31", "component32", "component33", "component34", "component35", "component36", "component37", "component38", "component39", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class SongData {
    @com.google.gson.annotations.SerializedName(value = "id")
    private final long id = 0L;
    @com.google.gson.annotations.SerializedName(value = "name")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String name = null;
    @com.google.gson.annotations.SerializedName(value = "pst")
    private final int pst = 0;
    @com.google.gson.annotations.SerializedName(value = "t")
    private final int t = 0;
    @com.google.gson.annotations.SerializedName(value = "ar")
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<me.wcy.music.common.bean.ArtistData> ar = null;
    @com.google.gson.annotations.SerializedName(value = "pop")
    private final int pop = 0;
    @com.google.gson.annotations.SerializedName(value = "st")
    private final int st = 0;
    @com.google.gson.annotations.SerializedName(value = "rt")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String rt = null;
    @com.google.gson.annotations.SerializedName(value = "fee")
    private final int fee = 0;
    @com.google.gson.annotations.SerializedName(value = "v")
    private final int v = 0;
    @com.google.gson.annotations.SerializedName(value = "cf")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String cf = null;
    @com.google.gson.annotations.SerializedName(value = "al")
    @org.jetbrains.annotations.NotNull()
    private final me.wcy.music.common.bean.AlbumData al = null;
    @com.google.gson.annotations.SerializedName(value = "dt")
    private final long dt = 0L;
    @com.google.gson.annotations.SerializedName(value = "h")
    @org.jetbrains.annotations.NotNull()
    private final me.wcy.music.common.bean.QualityData h = null;
    @com.google.gson.annotations.SerializedName(value = "m")
    @org.jetbrains.annotations.NotNull()
    private final me.wcy.music.common.bean.QualityData m = null;
    @com.google.gson.annotations.SerializedName(value = "l")
    @org.jetbrains.annotations.NotNull()
    private final me.wcy.music.common.bean.QualityData l = null;
    @com.google.gson.annotations.SerializedName(value = "sq")
    @org.jetbrains.annotations.NotNull()
    private final me.wcy.music.common.bean.QualityData sq = null;
    @com.google.gson.annotations.SerializedName(value = "hr")
    @org.jetbrains.annotations.NotNull()
    private final me.wcy.music.common.bean.QualityData hr = null;
    @com.google.gson.annotations.SerializedName(value = "cd")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String cd = null;
    @com.google.gson.annotations.SerializedName(value = "no")
    private final int no = 0;
    @com.google.gson.annotations.SerializedName(value = "ftype")
    private final int ftype = 0;
    @com.google.gson.annotations.SerializedName(value = "djId")
    private final int djId = 0;
    @com.google.gson.annotations.SerializedName(value = "copyright")
    private final int copyright = 0;
    @com.google.gson.annotations.SerializedName(value = "s_id")
    private final int sId = 0;
    @com.google.gson.annotations.SerializedName(value = "mark")
    private final int mark = 0;
    @com.google.gson.annotations.SerializedName(value = "originCoverType")
    private final int originCoverType = 0;
    @com.google.gson.annotations.SerializedName(value = "originSongSimpleData")
    @org.jetbrains.annotations.Nullable()
    private final me.wcy.music.common.bean.OriginSongSimpleData originSongSimpleData = null;
    @com.google.gson.annotations.SerializedName(value = "resourceState")
    private final boolean resourceState = false;
    @com.google.gson.annotations.SerializedName(value = "version")
    private final int version = 0;
    @com.google.gson.annotations.SerializedName(value = "single")
    private final int single = 0;
    @com.google.gson.annotations.SerializedName(value = "rtype")
    private final int rtype = 0;
    @com.google.gson.annotations.SerializedName(value = "mst")
    private final int mst = 0;
    @com.google.gson.annotations.SerializedName(value = "cp")
    private final int cp = 0;
    @com.google.gson.annotations.SerializedName(value = "mv")
    private final int mv = 0;
    @com.google.gson.annotations.SerializedName(value = "publishTime")
    private final int publishTime = 0;
    @com.google.gson.annotations.SerializedName(value = "reason")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String reason = null;
    @com.google.gson.annotations.SerializedName(value = "tns")
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> tns = null;
    @com.google.gson.annotations.SerializedName(value = "recommendReason")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String recommendReason = null;
    @com.google.gson.annotations.SerializedName(value = "alg")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String alg = null;
    
    public SongData(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, int pst, int t, @org.jetbrains.annotations.NotNull()
    java.util.List<me.wcy.music.common.bean.ArtistData> ar, int pop, int st, @org.jetbrains.annotations.NotNull()
    java.lang.String rt, int fee, int v, @org.jetbrains.annotations.NotNull()
    java.lang.String cf, @org.jetbrains.annotations.NotNull()
    me.wcy.music.common.bean.AlbumData al, long dt, @org.jetbrains.annotations.NotNull()
    me.wcy.music.common.bean.QualityData h, @org.jetbrains.annotations.NotNull()
    me.wcy.music.common.bean.QualityData m, @org.jetbrains.annotations.NotNull()
    me.wcy.music.common.bean.QualityData l, @org.jetbrains.annotations.NotNull()
    me.wcy.music.common.bean.QualityData sq, @org.jetbrains.annotations.NotNull()
    me.wcy.music.common.bean.QualityData hr, @org.jetbrains.annotations.NotNull()
    java.lang.String cd, int no, int ftype, int djId, int copyright, int sId, int mark, int originCoverType, @org.jetbrains.annotations.Nullable()
    me.wcy.music.common.bean.OriginSongSimpleData originSongSimpleData, boolean resourceState, int version, int single, int rtype, int mst, int cp, int mv, int publishTime, @org.jetbrains.annotations.NotNull()
    java.lang.String reason, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> tns, @org.jetbrains.annotations.NotNull()
    java.lang.String recommendReason, @org.jetbrains.annotations.NotNull()
    java.lang.String alg) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getName() {
        return null;
    }
    
    public final int getPst() {
        return 0;
    }
    
    public final int getT() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<me.wcy.music.common.bean.ArtistData> getAr() {
        return null;
    }
    
    public final int getPop() {
        return 0;
    }
    
    public final int getSt() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRt() {
        return null;
    }
    
    public final int getFee() {
        return 0;
    }
    
    public final int getV() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCf() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.common.bean.AlbumData getAl() {
        return null;
    }
    
    public final long getDt() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.common.bean.QualityData getH() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.common.bean.QualityData getM() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.common.bean.QualityData getL() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.common.bean.QualityData getSq() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.common.bean.QualityData getHr() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCd() {
        return null;
    }
    
    public final int getNo() {
        return 0;
    }
    
    public final int getFtype() {
        return 0;
    }
    
    public final int getDjId() {
        return 0;
    }
    
    public final int getCopyright() {
        return 0;
    }
    
    public final int getSId() {
        return 0;
    }
    
    public final int getMark() {
        return 0;
    }
    
    public final int getOriginCoverType() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final me.wcy.music.common.bean.OriginSongSimpleData getOriginSongSimpleData() {
        return null;
    }
    
    public final boolean getResourceState() {
        return false;
    }
    
    public final int getVersion() {
        return 0;
    }
    
    public final int getSingle() {
        return 0;
    }
    
    public final int getRtype() {
        return 0;
    }
    
    public final int getMst() {
        return 0;
    }
    
    public final int getCp() {
        return 0;
    }
    
    public final int getMv() {
        return 0;
    }
    
    public final int getPublishTime() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getReason() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getTns() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRecommendReason() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAlg() {
        return null;
    }
    
    public SongData() {
        super();
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final int component10() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.common.bean.AlbumData component12() {
        return null;
    }
    
    public final long component13() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.common.bean.QualityData component14() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.common.bean.QualityData component15() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.common.bean.QualityData component16() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.common.bean.QualityData component17() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.common.bean.QualityData component18() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component19() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    public final int component20() {
        return 0;
    }
    
    public final int component21() {
        return 0;
    }
    
    public final int component22() {
        return 0;
    }
    
    public final int component23() {
        return 0;
    }
    
    public final int component24() {
        return 0;
    }
    
    public final int component25() {
        return 0;
    }
    
    public final int component26() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final me.wcy.music.common.bean.OriginSongSimpleData component27() {
        return null;
    }
    
    public final boolean component28() {
        return false;
    }
    
    public final int component29() {
        return 0;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final int component30() {
        return 0;
    }
    
    public final int component31() {
        return 0;
    }
    
    public final int component32() {
        return 0;
    }
    
    public final int component33() {
        return 0;
    }
    
    public final int component34() {
        return 0;
    }
    
    public final int component35() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component36() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component37() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component38() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component39() {
        return null;
    }
    
    public final int component4() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<me.wcy.music.common.bean.ArtistData> component5() {
        return null;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final int component7() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    public final int component9() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.common.bean.SongData copy(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, int pst, int t, @org.jetbrains.annotations.NotNull()
    java.util.List<me.wcy.music.common.bean.ArtistData> ar, int pop, int st, @org.jetbrains.annotations.NotNull()
    java.lang.String rt, int fee, int v, @org.jetbrains.annotations.NotNull()
    java.lang.String cf, @org.jetbrains.annotations.NotNull()
    me.wcy.music.common.bean.AlbumData al, long dt, @org.jetbrains.annotations.NotNull()
    me.wcy.music.common.bean.QualityData h, @org.jetbrains.annotations.NotNull()
    me.wcy.music.common.bean.QualityData m, @org.jetbrains.annotations.NotNull()
    me.wcy.music.common.bean.QualityData l, @org.jetbrains.annotations.NotNull()
    me.wcy.music.common.bean.QualityData sq, @org.jetbrains.annotations.NotNull()
    me.wcy.music.common.bean.QualityData hr, @org.jetbrains.annotations.NotNull()
    java.lang.String cd, int no, int ftype, int djId, int copyright, int sId, int mark, int originCoverType, @org.jetbrains.annotations.Nullable()
    me.wcy.music.common.bean.OriginSongSimpleData originSongSimpleData, boolean resourceState, int version, int single, int rtype, int mst, int cp, int mv, int publishTime, @org.jetbrains.annotations.NotNull()
    java.lang.String reason, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> tns, @org.jetbrains.annotations.NotNull()
    java.lang.String recommendReason, @org.jetbrains.annotations.NotNull()
    java.lang.String alg) {
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