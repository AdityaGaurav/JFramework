(function(e) {
    var t = {
            common: {
                type: "line",
                lineColor: "#00f",
                fillColor: "#cdf",
                defaultPixelsPerValue: 3,
                width: "auto",
                height: "auto",
                composite: !1,
                tagValuesAttribute: "values",
                tagOptionsPrefix: "spark",
                enableTagOptions: !1
            },
            line: {
                spotColor: "#f80",
                spotRadius: 1.5,
                minSpotColor: "#f80",
                maxSpotColor: "#f80",
                lineWidth: 1,
                normalRangeMin: undefined,
                normalRangeMax: undefined,
                normalRangeColor: "#ccc",
                drawNormalOnTop: !1,
                chartRangeMin: undefined,
                chartRangeMax: undefined,
                chartRangeMinX: undefined,
                chartRangeMaxX: undefined
            },
            bar: {
                barColor: "#00f",
                negBarColor: "#f44",
                zeroColor: undefined,
                nullColor: undefined,
                zeroAxis: undefined,
                barWidth: 4,
                barSpacing: 1,
                chartRangeMax: undefined,
                chartRangeMin: undefined,
                chartRangeClip: !1,
                colorMap: undefined
            },
            tristate: {
                barWidth: 4,
                barSpacing: 1,
                posBarColor: "#6f6",
                negBarColor: "#f44",
                zeroBarColor: "#999",
                colorMap: {}
            },
            discrete: {
                lineHeight: "auto",
                thresholdColor: undefined,
                thresholdValue: 0,
                chartRangeMax: undefined,
                chartRangeMin: undefined,
                chartRangeClip: !1
            },
            bullet: {
                targetColor: "red",
                targetWidth: 3,
                performanceColor: "blue",
                rangeColors: ["#D3DAFE", "#A8B6FF", "#7F94FF"],
                base: undefined
            },
            pie: {
                sliceColors: ["#f00", "#0f0", "#00f"]
            },
            box: {
                raw: !1,
                boxLineColor: "black",
                boxFillColor: "#cdf",
                whiskerColor: "black",
                outlierLineColor: "#333",
                outlierFillColor: "white",
                medianColor: "red",
                showOutliers: !0,
                outlierIQR: 1.5,
                spotRadius: 1.5,
                target: undefined,
                targetColor: "#4a2",
                chartRangeMax: undefined,
                chartRangeMin: undefined
            }
        },
        n, r, i;
    e.fn.simpledraw = function(t, n, s) {
        if (s && this[0].VCanvas) return this[0].VCanvas;
        t === undefined && (t = e(this).innerWidth());
        n === undefined && (n = e(this).innerHeight());
        return e.browser.hasCanvas ? new r(t, n, this) : e.browser.msie ? new i(t, n, this) : !1
    };
    var s = [];
    e.fn.sparkline = function(t, n) {
        return this.each(function() {
            var r = new e.fn.sparkline.options(this, n),
                i = function() {
                    var n, i, s;
                    if (t === "html" || t === undefined) {
                        var o = this.getAttribute(r.get("tagValuesAttribute"));
                        if (o === undefined || o === null) o = e(this).html();
                        n = o.replace(/(^\s*<!--)|(-->\s*$)|\s+/g, "").split(",")
                    } else n = t;
                    i = r.get("width") == "auto" ? n.length * r.get("defaultPixelsPerValue") : r.get("width");
                    if (r.get("height") == "auto") {
                        if (!r.get("composite") || !this.VCanvas) {
                            var u = document.createElement("span");
                            u.innerHTML = "a";
                            e(this).html(u);
                            s = e(u).innerHeight();
                            e(u).remove()
                        }
                    } else s = r.get("height");
                    e.fn.sparkline[r.get("type")].call(this, n, r, i, s)
                };
            e(this).html() && e(this).is(":hidden") || e.fn.jquery < "1.3.0" && e(this).parents().is(":hidden") || !e(this).parents("body").length ? s.push([this, i]) : i.call(this)
        })
    };
    e.fn.sparkline.defaults = t;
    e.sparkline_display_visible = function() {
        for (var t = s.length - 1; t >= 0; t--) {
            var n = s[t][0];
            if (e(n).is(":visible") && !e(n).parents().is(":hidden")) {
                s[t][1].call(n);
                s.splice(t, 1)
            }
        }
    };
    var o = {},
        u = function(e) {
            switch (e) {
                case "undefined":
                    e = undefined;
                    break;
                case "null":
                    e = null;
                    break;
                case "true":
                    e = !0;
                    break;
                case "false":
                    e = !1;
                    break;
                default:
                    var t = parseFloat(e);
                    e == t && (e = t)
            }
            return e
        };
    e.fn.sparkline.options = function(t, n) {
        var r;
        this.userOptions = n = n || {};
        this.tag = t;
        this.tagValCache = {};
        var i = e.fn.sparkline.defaults,
            s = i.common;
        this.tagOptionsPrefix = n.enableTagOptions && (n.tagOptionsPrefix || s.tagOptionsPrefix);
        var u = this.getTagSetting("type");
        u === o ? r = i[n.type || s.type] : r = i[u];
        this.mergedOptions = e.extend({}, s, r, n)
    };
    e.fn.sparkline.options.prototype.getTagSetting = function(e) {
        var t, n, r = this.tagOptionsPrefix;
        if (r === !1 || r === undefined) return o;
        if (this.tagValCache.hasOwnProperty(e)) t = this.tagValCache.key;
        else {
            t = this.tag.getAttribute(r + e);
            if (t === undefined || t === null) t = o;
            else if (t.substr(0, 1) == "[") {
                t = t.substr(1, t.length - 2).split(",");
                for (n = t.length; n--;) t[n] = u(t[n].replace(/(^\s*)|(\s*$)/g, ""))
            } else if (t.substr(0, 1) == "{") {
                var i = t.substr(1, t.length - 2).split(",");
                t = {};
                for (n = i.length; n--;) {
                    var s = i[n].split(":", 2);
                    t[s[0].replace(/(^\s*)|(\s*$)/g, "")] = u(s[1].replace(/(^\s*)|(\s*$)/g, ""))
                }
            } else t = u(t);
            this.tagValCache.key = t
        }
        return t
    };
    e.fn.sparkline.options.prototype.get = function(e) {
        var t = this.getTagSetting(e);
        return t !== o ? t : this.mergedOptions[e]
    };
    e.fn.sparkline.line = function(t, n, r, i) {
        var s = [],
            o = [],
            u = [];
        for (var a = 0; a < t.length; a++) {
            var f = t[a],
                l = typeof t[a] == "string",
                c = typeof t[a] == "object" && t[a] instanceof Array,
                h = l && t[a].split(":");
            if (l && h.length == 2) {
                s.push(Number(h[0]));
                o.push(Number(h[1]));
                u.push(Number(h[1]))
            } else if (c) {
                s.push(f[0]);
                o.push(f[1]);
                u.push(f[1])
            } else {
                s.push(a);
                if (t[a] === null || t[a] == "null") o.push(null);
                else {
                    o.push(Number(f));
                    u.push(Number(f))
                }
            }
        }
        n.get("xvalues") && (s = n.get("xvalues"));
        var p = Math.max.apply(Math, u),
            d = p,
            v = Math.min.apply(Math, u),
            m = v,
            g = Math.max.apply(Math, s),
            y = Math.min.apply(Math, s),
            b = n.get("normalRangeMin"),
            w = n.get("normalRangeMax");
        if (b !== undefined) {
            b < v && (v = b);
            w > p && (p = w)
        }
        n.get("chartRangeMin") !== undefined && (n.get("chartRangeClip") || n.get("chartRangeMin") < v) && (v = n.get("chartRangeMin"));
        n.get("chartRangeMax") !== undefined && (n.get("chartRangeClip") || n.get("chartRangeMax") > p) && (p = n.get("chartRangeMax"));
        n.get("chartRangeMinX") !== undefined && (n.get("chartRangeClipX") || n.get("chartRangeMinX") < y) && (y = n.get("chartRangeMinX"));
        n.get("chartRangeMaxX") !== undefined && (n.get("chartRangeClipX") || n.get("chartRangeMaxX") > g) && (g = n.get("chartRangeMaxX"));
        var E = g - y === 0 ? 1 : g - y,
            S = p - v === 0 ? 1 : p - v,
            x = o.length - 1;
        if (x < 1) {
            this.innerHTML = "";
            return
        }
        var T = e(this).simpledraw(r, i, n.get("composite"));
        if (T) {
            var N = T.pixel_width,
                C = T.pixel_height,
                k = 0,
                L = 0,
                A = n.get("spotRadius");
            A && (N < A * 4 || C < A * 4) && (A = 0);
            if (A) {
                if (n.get("minSpotColor") || n.get("spotColor") && o[x] == v) C -= Math.ceil(A);
                if (n.get("maxSpotColor") || n.get("spotColor") && o[x] == p) {
                    C -= Math.ceil(A);
                    k += Math.ceil(A)
                }
                if (n.get("minSpotColor") || n.get("maxSpotColor") && (o[0] == v || o[0] == p)) {
                    L += Math.ceil(A);
                    N -= Math.ceil(A)
                }
                if (n.get("spotColor") || n.get("minSpotColor") || n.get("maxSpotColor") && (o[x] == v || o[x] == p)) N -= Math.ceil(A)
            }
            C--;
            var O = function() {
                if (b !== undefined) {
                    var e = k + Math.round(C - C * ((w - v) / S)),
                        t = Math.round(C * (w - b) / S);
                    T.drawRect(L, e, N, t, undefined, n.get("normalRangeColor"))
                }
            };
            n.get("drawNormalOnTop") || O();
            var M = [],
                _ = [M],
                D, P, H = o.length;
            for (a = 0; a < H; a++) {
                D = s[a];
                P = o[a];
                if (P === null) {
                    if (a && o[a - 1] !== null) {
                        M = [];
                        _.push(M)
                    }
                } else {
                    P < v && (P = v);
                    P > p && (P = p);
                    M.length || M.push([L + Math.round((D - y) * (N / E)), k + C]);
                    M.push([L + Math.round((D - y) * (N / E)), k + Math.round(C - C * ((P - v) / S))])
                }
            }
            var B = [],
                j = [],
                F = _.length;
            for (a = 0; a < F; a++) {
                M = _[a];
                if (!M.length) continue;
                if (n.get("fillColor")) {
                    M.push([M[M.length - 1][0], k + C - 1]);
                    j.push(M.slice(0));
                    M.pop()
                }
                M.length > 2 && (M[0] = [M[0][0], M[1][1]]);
                B.push(M)
            }
            F = j.length;
            for (a = 0; a < F; a++) T.drawShape(j[a], undefined, n.get("fillColor"));
            n.get("drawNormalOnTop") && O();
            F = B.length;
            for (a = 0; a < F; a++) T.drawShape(B[a], n.get("lineColor"), undefined, n.get("lineWidth"));
            A && n.get("spotColor") && T.drawCircle(L + Math.round(s[s.length - 1] * (N / E)), k + Math.round(C - C * ((o[x] - v) / S)), A, undefined, n.get("spotColor"));
            if (p != m) {
                if (A && n.get("minSpotColor")) {
                    D = s[e.inArray(m, o)];
                    T.drawCircle(L + Math.round((D - y) * (N / E)), k + Math.round(C - C * ((m - v) / S)), A, undefined, n.get("minSpotColor"))
                }
                if (A && n.get("maxSpotColor")) {
                    D = s[e.inArray(d, o)];
                    T.drawCircle(L + Math.round((D - y) * (N / E)), k + Math.round(C - C * ((d - v) / S)), A, undefined, n.get("maxSpotColor"))
                }
            }
        } else this.innerHTML = ""
    };
    e.fn.sparkline.bar = function(t, n, r, i) {
        r = t.length * n.get("barWidth") + (t.length - 1) * n.get("barSpacing");
        var s = [];
        for (var o = 0, u = t.length; o < u; o++)
            if (t[o] == "null" || t[o] === null) t[o] = null;
            else {
                t[o] = Number(t[o]);
                s.push(Number(t[o]))
            }
        var a = Math.max.apply(Math, s),
            f = Math.min.apply(Math, s);
        n.get("chartRangeMin") !== undefined && (n.get("chartRangeClip") || n.get("chartRangeMin") < f) && (f = n.get("chartRangeMin"));
        n.get("chartRangeMax") !== undefined && (n.get("chartRangeClip") || n.get("chartRangeMax") > a) && (a = n.get("chartRangeMax"));
        var l = n.get("zeroAxis");
        l === undefined && (l = f < 0);
        var c = a - f === 0 ? 1 : a - f,
            h, p;
        if (e.isArray(n.get("colorMap"))) {
            h = n.get("colorMap");
            p = null
        } else {
            h = null;
            p = n.get("colorMap")
        }
        var d = e(this).simpledraw(r, i, n.get("composite"));
        if (d) {
            var v, m = d.pixel_height,
                g = f < 0 && l ? m - Math.round(m * (Math.abs(f) / c)) - 1 : m - 1;
            for (o = t.length; o--;) {
                var y = o * (n.get("barWidth") + n.get("barSpacing")),
                    b, w = t[o];
                if (w === null) {
                    if (!n.get("nullColor")) continue;
                    v = n.get("nullColor");
                    w = l && f < 0 ? 0 : f;
                    i = 1;
                    b = l && f < 0 ? g : m - i
                } else {
                    w < f && (w = f);
                    w > a && (w = a);
                    v = w < 0 ? n.get("negBarColor") : n.get("barColor");
                    if (l && f < 0) {
                        i = Math.round(m * (Math.abs(w) / c)) + 1;
                        b = w < 0 ? g : g - i
                    } else {
                        i = Math.round(m * ((w - f) / c)) + 1;
                        b = m - i
                    }
                    w === 0 && n.get("zeroColor") !== undefined && (v = n.get("zeroColor"));
                    p && p[w] ? v = p[w] : h && h.length > o && (v = h[o]);
                    if (v === null) continue
                }
                d.drawRect(y, b, n.get("barWidth") - 1, i - 1, v, v)
            }
        } else this.innerHTML = ""
    };
    e.fn.sparkline.tristate = function(t, n, r, i) {
        t = e.map(t, Number);
        r = t.length * n.get("barWidth") + (t.length - 1) * n.get("barSpacing");
        var s, o;
        if (e.isArray(n.get("colorMap"))) {
            s = n.get("colorMap");
            o = null
        } else {
            s = null;
            o = n.get("colorMap")
        }
        var u = e(this).simpledraw(r, i, n.get("composite"));
        if (u) {
            var a = u.pixel_height,
                f = Math.round(a / 2);
            for (var l = t.length; l--;) {
                var c = l * (n.get("barWidth") + n.get("barSpacing")),
                    h, p;
                if (t[l] < 0) {
                    h = f;
                    i = f - 1;
                    p = n.get("negBarColor")
                } else if (t[l] > 0) {
                    h = 0;
                    i = f - 1;
                    p = n.get("posBarColor")
                } else {
                    h = f - 1;
                    i = 2;
                    p = n.get("zeroBarColor")
                }
                o && o[t[l]] ? p = o[t[l]] : s && s.length > l && (p = s[l]);
                if (p === null) continue;
                u.drawRect(c, h, n.get("barWidth") - 1, i - 1, p, p)
            }
        } else this.innerHTML = ""
    };
    e.fn.sparkline.discrete = function(t, n, r, i) {
        t = e.map(t, Number);
        r = n.get("width") == "auto" ? t.length * 2 : r;
        var s = Math.floor(r / t.length),
            o = e(this).simpledraw(r, i, n.get("composite"));
        if (o) {
            var u = o.pixel_height,
                a = n.get("lineHeight") == "auto" ? Math.round(u * .3) : n.get("lineHeight"),
                f = u - a,
                l = Math.min.apply(Math, t),
                c = Math.max.apply(Math, t);
            n.get("chartRangeMin") !== undefined && (n.get("chartRangeClip") || n.get("chartRangeMin") < l) && (l = n.get("chartRangeMin"));
            n.get("chartRangeMax") !== undefined && (n.get("chartRangeClip") || n.get("chartRangeMax") > c) && (c = n.get("chartRangeMax"));
            var h = c - l;
            for (var p = t.length; p--;) {
                var d = t[p];
                d < l && (d = l);
                d > c && (d = c);
                var v = p * s,
                    m = Math.round(f - f * ((d - l) / h));
                o.drawLine(v, m, v, m + a, n.get("thresholdColor") && d < n.get("thresholdValue") ? n.get("thresholdColor") : n.get("lineColor"))
            }
        } else this.innerHTML = ""
    };
    e.fn.sparkline.bullet = function(t, n, r, i) {
        t = e.map(t, Number);
        r = n.get("width") == "auto" ? "4.0em" : r;
        var s = e(this).simpledraw(r, i, n.get("composite"));
        if (s && t.length > 1) {
            var o = s.pixel_width - Math.ceil(n.get("targetWidth") / 2),
                u = s.pixel_height,
                a = Math.min.apply(Math, t),
                f = Math.max.apply(Math, t);
            n.get("base") === undefined ? a = a < 0 ? a : 0 : a = n.get("base");
            var l = f - a;
            for (var c = 2, h = t.length; c < h; c++) {
                var p = t[c],
                    d = Math.round(o * ((p - a) / l));
                s.drawRect(0, 0, d - 1, u - 1, n.get("rangeColors")[c - 2], n.get("rangeColors")[c - 2])
            }
            var v = t[1],
                m = Math.round(o * ((v - a) / l));
            s.drawRect(0, Math.round(u * .3), m - 1, Math.round(u * .4) - 1, n.get("performanceColor"), n.get("performanceColor"));
            var g = t[0],
                y = Math.round(o * ((g - a) / l) - n.get("targetWidth") / 2),
                b = Math.round(u * .1),
                w = u - b * 2;
            s.drawRect(y, b, n.get("targetWidth") - 1, w - 1, n.get("targetColor"), n.get("targetColor"))
        } else this.innerHTML = ""
    };
    e.fn.sparkline.pie = function(t, n, r, i) {
        t = e.map(t, Number);
        r = n.get("width") == "auto" ? i : r;
        var s = e(this).simpledraw(r, i, n.get("composite"));
        if (s && t.length > 1) {
            var o = s.pixel_width,
                u = s.pixel_height,
                a = Math.floor(Math.min(o, u) / 2),
                f = 0,
                l = 0,
                c = 2 * Math.PI;
            for (var h = t.length; h--;) f += t[h];
            n.get("offset") && (l += 2 * Math.PI * (n.get("offset") / 360));
            var p = t.length;
            for (h = 0; h < p; h++) {
                var d = l,
                    v = l;
                f > 0 && (v = l + c * (t[h] / f));
                s.drawPieSlice(a, a, a, d, v, undefined, n.get("sliceColors")[h % n.get("sliceColors").length]);
                l = v
            }
        }
    };
    var a = function(e, t) {
        if (t == 2) {
            var n = Math.floor(e.length / 2);
            return e.length % 2 ? e[n] : (e[n] + e[n + 1]) / 2
        }
        var r = Math.floor(e.length / 4);
        return e.length % 2 ? (e[r * t] + e[r * t + 1]) / 2 : e[r * t]
    };
    e.fn.sparkline.box = function(t, n, r, i) {
        t = e.map(t, Number);
        r = n.get("width") == "auto" ? "4.0em" : r;
        var s = n.get("chartRangeMin") === undefined ? Math.min.apply(Math, t) : n.get("chartRangeMin"),
            o = n.get("chartRangeMax") === undefined ? Math.max.apply(Math, t) : n.get("chartRangeMax"),
            u = e(this).simpledraw(r, i, n.get("composite")),
            f = t.length,
            l, c, h, p, d, v, m;
        if (u && t.length > 1) {
            var g = u.pixel_width,
                y = u.pixel_height;
            if (n.get("raw"))
                if (n.get("showOutliers") && t.length > 5) {
                    c = t[0];
                    l = t[1];
                    h = t[2];
                    p = t[3];
                    d = t[4];
                    v = t[5];
                    m = t[6]
                } else {
                    l = t[0];
                    h = t[1];
                    p = t[2];
                    d = t[3];
                    v = t[4]
                } else {
                t.sort(function(e, t) {
                    return e - t
                });
                h = a(t, 1);
                p = a(t, 2);
                d = a(t, 3);
                var b = d - h;
                if (n.get("showOutliers")) {
                    l = undefined;
                    v = undefined;
                    for (var w = 0; w < f; w++) {
                        l === undefined && t[w] > h - b * n.get("outlierIQR") && (l = t[w]);
                        t[w] < d + b * n.get("outlierIQR") && (v = t[w])
                    }
                    c = t[0];
                    m = t[f - 1]
                } else {
                    l = t[0];
                    v = t[f - 1]
                }
            }
            var E = g / (o - s + 1),
                S = 0;
            if (n.get("showOutliers")) {
                S = Math.ceil(n.get("spotRadius"));
                g -= 2 * Math.ceil(n.get("spotRadius"));
                E = g / (o - s + 1);
                c < l && u.drawCircle((c - s) * E + S, y / 2, n.get("spotRadius"), n.get("outlierLineColor"), n.get("outlierFillColor"));
                m > v && u.drawCircle((m - s) * E + S, y / 2, n.get("spotRadius"), n.get("outlierLineColor"), n.get("outlierFillColor"))
            }
            u.drawRect(Math.round((h - s) * E + S), Math.round(y * .1), Math.round((d - h) * E), Math.round(y * .8), n.get("boxLineColor"), n.get("boxFillColor"));
            u.drawLine(Math.round((l - s) * E + S), Math.round(y / 2), Math.round((h - s) * E + S), Math.round(y / 2), n.get("lineColor"));
            u.drawLine(Math.round((l - s) * E + S), Math.round(y / 4), Math.round((l - s) * E + S), Math.round(y - y / 4), n.get("whiskerColor"));
            u.drawLine(Math.round((v - s) * E + S), Math.round(y / 2), Math.round((d - s) * E + S), Math.round(y / 2), n.get("lineColor"));
            u.drawLine(Math.round((v - s) * E + S), Math.round(y / 4), Math.round((v - s) * E + S), Math.round(y - y / 4), n.get("whiskerColor"));
            u.drawLine(Math.round((p - s) * E + S), Math.round(y * .1), Math.round((p - s) * E + S), Math.round(y * .9), n.get("medianColor"));
            if (n.get("target")) {
                var x = Math.ceil(n.get("spotRadius"));
                u.drawLine(Math.round((n.get("target") - s) * E + S), Math.round(y / 2 - x), Math.round((n.get("target") - s) * E + S), Math.round(y / 2 + x), n.get("targetColor"));
                u.drawLine(Math.round((n.get("target") - s) * E + S - x), Math.round(y / 2), Math.round((n.get("target") - s) * E + S + x), Math.round(y / 2), n.get("targetColor"))
            }
        } else this.innerHTML = ""
    };
    e.browser.msie && !document.namespaces.v && document.namespaces.add("v", "urn:schemas-microsoft-com:vml", "#default#VML");
    if (e.browser.hasCanvas === undefined) {
        var f = document.createElement("canvas");
        e.browser.hasCanvas = f.getContext !== undefined
    }
    n = function(e, t, n) {};
    n.prototype = {
        init: function(e, t, n) {
            this.width = e;
            this.height = t;
            this.target = n;
            n[0] && (n = n[0]);
            n.VCanvas = this
        },
        drawShape: function(e, t, n, r) {
            alert("drawShape not implemented")
        },
        drawLine: function(e, t, n, r, i, s) {
            return this.drawShape([
                [e, t],
                [n, r]
            ], i, s)
        },
        drawCircle: function(e, t, n, r, i) {
            alert("drawCircle not implemented")
        },
        drawPieSlice: function(e, t, n, r, i, s, o) {
            alert("drawPieSlice not implemented")
        },
        drawRect: function(e, t, n, r, i, s) {
            alert("drawRect not implemented")
        },
        getElement: function() {
            return this.canvas
        },
        _insert: function(t, n) {
            e(n).html(t)
        }
    };
    r = function(e, t, n) {
        return this.init(e, t, n)
    };
    r.prototype = e.extend(new n, {
        _super: n.prototype,
        init: function(t, n, r) {
            this._super.init(t, n, r);
            this.canvas = document.createElement("canvas");
            r[0] && (r = r[0]);
            r.VCanvas = this;
            e(this.canvas).css({
                display: "inline-block",
                width: t,
                height: n,
                verticalAlign: "top"
            });
            this._insert(this.canvas, r);
            this.pixel_height = e(this.canvas).height();
            this.pixel_width = e(this.canvas).width();
            this.canvas.width = this.pixel_width;
            this.canvas.height = this.pixel_height;
            e(this.canvas).css({
                width: this.pixel_width,
                height: this.pixel_height
            })
        },
        _getContext: function(e, t, n) {
            var r = this.canvas.getContext("2d");
            e !== undefined && (r.strokeStyle = e);
            r.lineWidth = n === undefined ? 1 : n;
            t !== undefined && (r.fillStyle = t);
            return r
        },
        drawShape: function(e, t, n, r) {
            var i = this._getContext(t, n, r);
            i.beginPath();
            i.moveTo(e[0][0] + .5, e[0][1] + .5);
            for (var s = 1, o = e.length; s < o; s++) i.lineTo(e[s][0] + .5, e[s][1] + .5);
            t !== undefined && i.stroke();
            n !== undefined && i.fill()
        },
        drawCircle: function(e, t, n, r, i) {
            var s = this._getContext(r, i);
            s.beginPath();
            s.arc(e, t, n, 0, 2 * Math.PI, !1);
            r !== undefined && s.stroke();
            i !== undefined && s.fill()
        },
        drawPieSlice: function(e, t, n, r, i, s, o) {
            var u = this._getContext(s, o);
            u.beginPath();
            u.moveTo(e, t);
            u.arc(e, t, n, r, i, !1);
            u.lineTo(e, t);
            u.closePath();
            s !== undefined && u.stroke();
            o && u.fill()
        },
        drawRect: function(e, t, n, r, i, s) {
            return this.drawShape([
                [e, t],
                [e + n, t],
                [e + n, t + r],
                [e, t + r],
                [e, t]
            ], i, s)
        }
    });
    i = function(e, t, n) {
        return this.init(e, t, n)
    };
    i.prototype = e.extend(new n, {
        _super: n.prototype,
        init: function(t, n, r) {
            this._super.init(t, n, r);
            r[0] && (r = r[0]);
            r.VCanvas = this;
            this.canvas = document.createElement("span");
            e(this.canvas).css({
                display: "inline-block",
                position: "relative",
                overflow: "hidden",
                width: t,
                height: n,
                margin: "0px",
                padding: "0px",
                verticalAlign: "top"
            });
            this._insert(this.canvas, r);
            this.pixel_height = e(this.canvas).height();
            this.pixel_width = e(this.canvas).width();
            this.canvas.width = this.pixel_width;
            this.canvas.height = this.pixel_height;
            var i = '<v:group coordorigin="0 0" coordsize="' + this.pixel_width + " " + this.pixel_height + '"' + ' style="position:absolute;top:0;left:0;width:' + this.pixel_width + "px;height=" + this.pixel_height + 'px;"></v:group>';
            this.canvas.insertAdjacentHTML("beforeEnd", i);
            this.group = e(this.canvas).children()[0]
        },
        drawShape: function(e, t, n, r) {
            var i = [];
            for (var s = 0, o = e.length; s < o; s++) i[s] = "" + e[s][0] + "," + e[s][1];
            var u = i.splice(0, 1);
            r = r === undefined ? 1 : r;
            var a = t === undefined ? ' stroked="false" ' : ' strokeWeight="' + r + '" strokeColor="' + t + '" ',
                f = n === undefined ? ' filled="false"' : ' fillColor="' + n + '" filled="true" ',
                l = i[0] == i[i.length - 1] ? "x " : "",
                c = '<v:shape coordorigin="0 0" coordsize="' + this.pixel_width + " " + this.pixel_height + '" ' + a + f + ' style="position:absolute;left:0px;top:0px;height:' + this.pixel_height + "px;width:" + this.pixel_width + 'px;padding:0px;margin:0px;" ' + ' path="m ' + u + " l " + i.join(", ") + " " + l + 'e">' + " </v:shape>";
            this.group.insertAdjacentHTML("beforeEnd", c)
        },
        drawCircle: function(e, t, n, r, i) {
            e -= n + 1;
            t -= n + 1;
            var s = r === undefined ? ' stroked="false" ' : ' strokeWeight="1" strokeColor="' + r + '" ',
                o = i === undefined ? ' filled="false"' : ' fillColor="' + i + '" filled="true" ',
                u = "<v:oval " + s + o + ' style="position:absolute;top:' + t + "px; left:" + e + "px; width:" + n * 2 + "px; height:" + n * 2 + 'px"></v:oval>';
            this.group.insertAdjacentHTML("beforeEnd", u)
        },
        drawPieSlice: function(e, t, n, r, i, s, o) {
            if (r == i) return;
            if (i - r == 2 * Math.PI) {
                r = 0;
                i = 2 * Math.PI
            }
            var u = e + Math.round(Math.cos(r) * n),
                a = t + Math.round(Math.sin(r) * n),
                f = e + Math.round(Math.cos(i) * n),
                l = t + Math.round(Math.sin(i) * n);
            if (u == f && a == l && i - r < Math.PI) return;
            var c = [e - n, t - n, e + n, t + n, u, a, f, l],
                h = s === undefined ? ' stroked="false" ' : ' strokeWeight="1" strokeColor="' + s + '" ',
                p = o === undefined ? ' filled="false"' : ' fillColor="' + o + '" filled="true" ',
                d = '<v:shape coordorigin="0 0" coordsize="' + this.pixel_width + " " + this.pixel_height + '" ' + h + p + ' style="position:absolute;left:0px;top:0px;height:' + this.pixel_height + "px;width:" + this.pixel_width + 'px;padding:0px;margin:0px;" ' + ' path="m ' + e + "," + t + " wa " + c.join(", ") + ' x e">' + " </v:shape>";
            this.group.insertAdjacentHTML("beforeEnd", d)
        },
        drawRect: function(e, t, n, r, i, s) {
            return this.drawShape([
                [e, t],
                [e, t + r],
                [e + n, t + r],
                [e + n, t],
                [e, t]
            ], i, s)
        }
    })
})(jQuery);