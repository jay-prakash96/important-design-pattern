package com.example.designpatterns.gof;

/**
 * Command Pattern Example
 *
 * Definition: Encapsulates a request as an object, allowing parameterization and queuing of requests.
 *
 * Real-life analogy: A text editor where each user action (type, undo, redo) is a command object.
 *
 * Interview explanation:
 * - Command is used for undo/redo, macro recording, and task scheduling.
 * - "Suppose you want to support undo/redo in a text editor. Each action is a command object, so you can execute, undo, or redo it."
 *
 * Real-life Example: Simple text editor command queue
 */
public class CommandExample {
    // Command interface
    public interface EditorCommand {
        String execute();
        String undo();
    }

    // Receiver (the editor)
    public static class TextEditor {
        private final StringBuilder content = new StringBuilder();
        public void type(String text) { content.append(text); }
        public void delete(int length) {
            int start = Math.max(0, content.length() - length);
            content.delete(start, content.length());
        }
        public String getContent() { return content.toString(); }
    }

    // Concrete command: Type
    public static class TypeCommand implements EditorCommand {
        private final TextEditor editor;
        private final String text;
        public TypeCommand(TextEditor editor, String text) {
            this.editor = editor;
            this.text = text;
        }
        public String execute() { editor.type(text); return "Typed: " + text; }
        public String undo() { editor.delete(text.length()); return "Undo type: " + text; }
    }

    // Command Invoker
    public static class EditorInvoker {
        private final TextEditor editor = new TextEditor();
        private final java.util.Stack<EditorCommand> history = new java.util.Stack<>();
        public String executeCommand(EditorCommand command) {
            String result = command.execute();
            history.push(command);
            return result;
        }
        public String undoLast() {
            if (!history.isEmpty()) {
                EditorCommand last = history.pop();
                return last.undo();
            }
            return "Nothing to undo.";
        }
        public String getContent() { return editor.getContent(); }
        public TextEditor getEditor() { return editor; }
    }
}
