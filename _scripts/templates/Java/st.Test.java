// Generated from trgen <version>

import java.io.FileNotFoundException;
import java.io.IOException;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.tree.ParseTree;
import java.time.Instant;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.io.File;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;

public class Test {

    static boolean tee = false;
    static boolean show_profile = false;
    static boolean show_tree = false;
    static boolean show_tokens = false;
    static boolean show_trace = false;
    static boolean show_diagnostic = false;
    static int error_code = 0;
    static String file_encoding = "<file_encoding>";
    static boolean binary = <binary>;
    static int string_instance = 0;
    static String prefix = "";
    static boolean quiet = false;

    public static void main(String[] args) throws  FileNotFoundException, IOException
    {
        List\<Boolean> is_fns = new ArrayList\<Boolean>();
        List\<String> inputs = new ArrayList\<String>();
        for (int i = 0; i \< args.length; ++i)
        {
            if (args[i].equals("-d"))
            {
                show_diagnostic = true;
            }
            else if (args[i].equals("-profile"))
            {
                show_profile = true;
            }
            else if (args[i].equals("-tokens"))
            {
                show_tokens = true;
            }
            else if (args[i].equals("-tree"))
            {
                show_tree = true;
            }
            else if (args[i].equals("-prefix"))
            {
                prefix = args[++i] + " ";
            }
            else if (args[i].equals("-input")) {
                inputs.add(args[++i]);
                is_fns.add(false);
            }
            else if (args[i].equals("-tee"))
            {
                tee = true;
            }
            else if (args[i].equals("-encoding"))
            {
                file_encoding = args[++i];
            }
            else if (args[i].equals("-x"))
            {
                Scanner scanner = new Scanner(System.in);
                for (; ; )
                {
                    if (!scanner.hasNext()) break;
                    String line = scanner.nextLine();
                    if (line == null) break;
                    line = line.trim();
                    if (line == "") break;
                    inputs.add(line);
                    is_fns.add(true);
                }
            }
            else if (args[i].equals("-q"))
            {
                quiet = true;
            }
            else if (args[i].equals("-trace"))
            {
                show_trace = true;
                continue;
            }
            else {
                inputs.add(args[i]);
                is_fns.add(true);
            }
        }
        CharStream str = null;
        if (inputs.size() == 0)
        {
            ParseStdin();
        }
        else
        {
            Instant start = Instant.now();
            for (int f = 0; f \< inputs.size(); ++f)
            {
                if (is_fns.get(f))
                    ParseFilename(inputs.get(f), f);
                else
                    ParseString(inputs.get(f), f);
            }
            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).toMillis();
            if (!quiet) System.err.println(prefix + "Total Time: " + (timeElapsed * 1.0) / 1000.0);
        }
        java.lang.System.exit(error_code);
    }

    static void ParseStdin()throws IOException {
        CharStream str = CharStreams.fromStream(System.in);
        DoParse(str, "stdin", 0);
    }

    static void ParseString(String input, int row_number) throws IOException {
        var str = CharStreams.fromString(input);
        DoParse(str, "string" + string_instance++, row_number);
    }

    static void ParseFilename(String input, int row_number) throws IOException
    {
        CharStream str = null;
        if (file_encoding == null || file_encoding == "")
            str = CharStreams.fromFileName(input);
        else {
            var charset = java.nio.charset.Charset.forName(file_encoding);
            str = CharStreams.fromFileName(input, charset);
        }
        DoParse(str, input, row_number);
    }

    static void DoParse(CharStream str, String input_name, int row_number) {
        if (binary) str = new BinaryCharStream(str);
        <lexer_name> lexer = new <lexer_name>(str);
        if (show_tokens)
        {
            StringBuilder new_s = new StringBuilder();
            for (int i = 0; ; ++i)
            {
                var ro_token = lexer.nextToken();
                var token = (CommonToken)ro_token;
                token.setTokenIndex(i);
                new_s.append(token.toString());
                new_s.append(System.getProperty("line.separator"));
                if (token.getType() == IntStream.EOF)
                    break;
            }
            System.err.println(new_s.toString());
            lexer.reset();
        }
        var tokens = new CommonTokenStream(lexer);
        <parser_name> parser = new <parser_name>(tokens);
        PrintStream output = null;
        try {
            output = tee ? new PrintStream(new File(input_name + ".errors")) : System.out;
        } catch (NullPointerException e) {
            output = System.err;
        } catch (FileNotFoundException e2) {
            output = System.err;
        }
        ErrorListener listener_lexer = new ErrorListener(quiet, tee, output);
        ErrorListener listener_parser = new ErrorListener(quiet, tee, output);
        parser.removeErrorListeners();
        lexer.removeErrorListeners();
        parser.addErrorListener(listener_parser);
        lexer.addErrorListener(listener_lexer);
        if (show_diagnostic)
        {
            parser.addErrorListener(new MyDiagnosticErrorListener());
        }
        if (show_trace)
        {
            parser.setTrace(true);
//            ParserATNSimulator.trace_atn_sim = true;
        }
        Instant start = Instant.now();
        ParseTree tree = parser.<start_symbol>();
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        String result = "";
        if (listener_parser.had_error || listener_lexer.had_error)
        {
            result = "fail";
            error_code = 1;
        }
        else
            result = "success";
        if (show_tree)
        {
            if (tee)
            {
                PrintWriter treef = null;
                try {
                    treef = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File(input_name + ".tree")), StandardCharsets.UTF_8), true);
                    //treef = new PrintStream(new File(input_name + ".tree"));
                } catch (NullPointerException e) {
                    treef = new PrintWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8), true);;
                } catch (FileNotFoundException e2) {
                    treef = new PrintWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8), true);
                }
                treef.print(tree.toStringTree(parser));
                treef.close();
            } else
            {
                System.err.println(tree.toStringTree(parser));
            }
        }
        if (!quiet)
        {
            System.err.println(prefix + "Java " + row_number + " " + input_name + " " + result + " " + (timeElapsed * 1.0) / 1000.0);
        }
        if (tee) output.close();
    }

    public static String toStringTree(Tree tree, Parser recog) {
        StringBuilder sb = new StringBuilder();
        String[] ruleNames = recog != null ? recog.getRuleNames() : null;
        List\<String> ruleNamesList = ruleNames != null ? List.of(ruleNames) : null;
        toStringTree(sb, tree, 0, ruleNamesList);
        return sb.toString();
    }

    public static void toStringTree(StringBuilder sb, Tree t, int indent, List\<String> ruleNames) {
        String s = org.antlr.v4.runtime.misc.Utils.escapeWhitespace(getNodeText(t, ruleNames), false);
        if (t.getChildCount() == 0) {
            for (int i = 0; i \< indent; ++i) sb.append(" ");
            sb.append(s).append(System.lineSeparator());
            return;
        }
        s = org.antlr.v4.runtime.misc.Utils.escapeWhitespace(getNodeText(t, ruleNames), false);
        for (int i = 0; i \< indent; ++i) sb.append(' ');
        sb.append(s).append(System.lineSeparator());
        for (int i = 0; i \< t.getChildCount(); i++) {
            toStringTree(sb, t.getChild(i), indent + 1, ruleNames);
        }
    }

    public static String getNodeText(Tree t, Parser recog) {
        String[] ruleNames = recog != null ? recog.getRuleNames() : null;
        List\<String> ruleNamesList = ruleNames != null ? java.util.Arrays.asList(ruleNames) : null;
        return getNodeText(t, ruleNamesList);
    }
    
    public static String getNodeText(Tree t, List\<String> ruleNames) {
        if ( ruleNames!=null ) {
            if ( t instanceof RuleContext ) {
                int ruleIndex = ((RuleContext)t).getRuleContext().getRuleIndex();
                String ruleName = ruleNames.get(ruleIndex);
                int altNumber = ((RuleContext) t).getAltNumber();
                if ( altNumber!=ATN.INVALID_ALT_NUMBER ) {
                    return ruleName+":"+altNumber;
                }
                return ruleName;
            }
            else if ( t instanceof ErrorNode) {
                return t.toString();
            }
            else if ( t instanceof TerminalNode) {
                Token symbol = ((TerminalNode)t).getSymbol();
                if (symbol != null) {
                    String s = symbol.getText();
                    return s;
                }
            }
        }
                // no recog for rule names
        Object payload = t.getPayload();
        if ( payload instanceof Token ) {
            return ((Token)payload).getText();
        }
        return t.getPayload().toString();
    }

}
