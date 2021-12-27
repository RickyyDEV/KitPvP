package ridev.com.br.utils.files;


import ridev.com.br.Main;

import java.io.*;
import java.util.Objects;
import java.util.logging.Level;

public class FileUtils {


    /**
     * Deleta um arquivo, se for uma pasta ir� deletar e ele e tudo que tem dentro.
     *
     * @param file O arquivo para deletar.
     */
    public static void deleteFile(File file) {
        if (file.isDirectory()) {
            for (File delete : Objects.requireNonNull(file.listFiles())) {
                deleteFile(delete);
            }
        }

        file.delete();
    }

    /**
     * Copia um arquivo apartir de um InputStream.
     *
     * @param input O input para ser copiado.
     * @param out   O arquivo destinario.
     */
    public static void copyFile(InputStream input, File out) {
        FileOutputStream ou = null;
        try {
            ou = new FileOutputStream(out);
            byte[] buff = new byte[1024];
            int len;
            while ((len = input.read(buff)) > 0) {
                ou.write(buff, 0, len);
            }
        } catch (IOException ex) {
            Main.LOGGER.log(Level.WARNING, "Failed at copy file " + out.getName() + "!", ex);
        } finally {
            try {
                if (ou != null) {
                    ou.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (IOException ignored) {
            }
        }
    }

}
