/**
 * 将驼峰式命名的字符串转换为下划线方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。<br>
 * 例如：
 *
 * <pre>
 * HelloWorld=》hello_world
 * Hello_World=》hello_world
 * HelloWorld_test=》hello_world_test
 * </pre>
 *
 * @param str 转换前的驼峰式命名的字符串，也可以为下划线形式
 * @return 转换后下划线方式命名的字符串
 */
function camel2underline(str) {
    if (str == null) {
        return null;
    }
    var length = str.length;
    var sb = "";
    var c;
    for (var i = 0; i < length; i++) {
        c = str.charAt(i);
        var preChar = (i > 0) ? str.charAt(i - 1) : null;
        if ('A'<=c&&c<='Z') {
            // 遇到大写字母处理
            var nextChar = (i < str.length - 1) ? str.charAt(i + 1) : null;
            if (null != preChar && 'A'<=preChar&&preChar<='Z') {
                // 前一个字符为大写，则按照一个词对待，例如AB
                sb+=(c);
            } else if (null != nextChar && ('a'<nextChar||nextChar>'z')) {
                // 后一个为非小写字母，按照一个词对待
                if (null != preChar && '_' !== preChar) {
                    // 前一个是非大写时按照新词对待，加连接符，例如xAB
                    sb+=('_');
                }
                sb+=(c);
            } else {
                // 前后都为非大写按照新词对待
                if (null != preChar && '_' !== preChar) {
                    // 前一个非连接符，补充连接符
                    sb+=('_');
                }
                sb+=(String.fromCharCode(c.charCodeAt(0)^32));
            }
        } else {
            if ('_' !== c
                && sb.length > 0
                && 'A'<=sb.charAt(-1)&&sb.charAt(-1)<='Z'
                && 'a'<=c&&c<='z') {
                // 当结果中前一个字母为大写，当前为小写(非数字或字符)，说明此字符为新词开始（连接符也表示新词）
                sb+=('_');
            }
            // 小写或符号
            sb+=(c);
        }
    }
    return sb;
}

let s = camel2underline("whoAreYou");
console.log(s);
