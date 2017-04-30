/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.clcworld.destinystickers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.StatFs;
import android.provider.DocumentsContract;
import android.provider.DocumentsContract.Document;
import android.provider.DocumentsContract.Root;
import android.provider.DocumentsProvider;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
//---------------------------------------------------------
// Uses Android's Storage Access Framework (SAF)
// to provide Destiny stickers to any app. :)
//
// author: CLC
//
// Stickers are from Kevin Raganit (kevinraganit.com);
// I'm just trying to make it easier for fellow Android
// Guardians to enjoy the awesomeness.
//
// Hat tip to ianhanniballake whose excellent LocalStorage 
// project and Medium post were great tutorials for learning
// how to create a custom DocumentsProvider.
// https://github.com/ianhanniballake/LocalStorage
// https://medium.com/google-developers/building-a-documentsprovider-f7f2fb38e86a
//---------------------------------------------------------
public class StickerProvider extends DocumentsProvider {
    /**
     * Default root projection: everything but Root.COLUMN_MIME_TYPES
     */
    private final static String[] DEFAULT_ROOT_PROJECTION = new String[]{Root.COLUMN_ROOT_ID, Root.COLUMN_SUMMARY,
            Root.COLUMN_FLAGS, Root.COLUMN_TITLE, Root.COLUMN_DOCUMENT_ID, Root.COLUMN_ICON,
            Root.COLUMN_AVAILABLE_BYTES};
    /**
     * Default document projection: everything but Document.COLUMN_ICON and Document.COLUMN_SUMMARY
     */
    private final static String[] DEFAULT_DOCUMENT_PROJECTION = new String[]{Document.COLUMN_DOCUMENT_ID,
            Document.COLUMN_DISPLAY_NAME, Document.COLUMN_FLAGS, Document.COLUMN_MIME_TYPE, Document.COLUMN_SIZE,
            Document.COLUMN_LAST_MODIFIED};

    @Override
    public Cursor queryRoots(final String[] projection) throws FileNotFoundException {
        if (getContext() == null) {
            return null;
        }
        // Create a cursor with either the requested fields, or the default projection if "projection" is null.
        final MatrixCursor result = new MatrixCursor(projection != null ? projection : DEFAULT_ROOT_PROJECTION);

        final MatrixCursor.RowBuilder row = result.newRow();
        // These columns are required
        row.add(Root.COLUMN_ROOT_ID, "net.clcworld.destinystickers");
        row.add(Root.COLUMN_DOCUMENT_ID, "net.clcworld.destinystickers");
        row.add(Root.COLUMN_TITLE, getContext().getString(R.string.stickers_destiny));
        row.add(Root.COLUMN_FLAGS, Root.FLAG_LOCAL_ONLY | Root.FLAG_SUPPORTS_IS_CHILD);
        row.add(Root.COLUMN_ICON, R.drawable.stickers_ic_launcher);
        return result;
    }

    @Override
    public AssetFileDescriptor openDocumentThumbnail(final String documentId, final Point sizeHint,
                                                     final CancellationSignal signal) throws FileNotFoundException {
        return getContext().getResources().openRawResourceFd(Integer.parseInt(documentId));
    }

    @Override
    public boolean isChildDocument(final String parentDocumentId, final String documentId) {
        return documentId.startsWith(parentDocumentId);
    }

    @Override
    public Cursor queryChildDocuments(final String parentDocumentId, final String[] projection,
                                      final String sortOrder) throws FileNotFoundException {
        // Create a cursor with either the requested fields, or the default projection if "projection" is null.
        final MatrixCursor result = new MatrixCursor(projection != null ? projection : DEFAULT_DOCUMENT_PROJECTION);

        // Load the stickers here - since documentId has to be a string, we're going to convert the R.raw.RESID
        // into a string for now and then parse it back to an int when we use it to retrieve the sticker.
        includeAsset(result, R.raw.stickers_amanda_kiss + "", getContext().getString(R.string.stickers_amanda_kiss));
        includeAsset(result, R.raw.stickers_babycrota + "", getContext().getString(R.string.stickers_babycrota));
        includeAsset(result, R.raw.stickers_banshee + "", getContext().getString(R.string.stickers_banshee));
        includeAsset(result, R.raw.stickers_cayde_sneaky + "", getContext().getString(R.string.stickers_cayde_sneaky));
        includeAsset(result, R.raw.stickers_cayde_thumbsup + "", getContext().getString(R.string.stickers_cayde_thumbsup));
        includeAsset(result, R.raw.stickers_eris + "", getContext().getString(R.string.stickers_eris));
        includeAsset(result, R.raw.stickers_fenchurch + "", getContext().getString(R.string.stickers_fenchurch));
        includeAsset(result, R.raw.stickers_ghost + "", getContext().getString(R.string.stickers_ghost));
        includeAsset(result, R.raw.stickers_guardian_down + "", getContext().getString(R.string.stickers_guardian_down));
        includeAsset(result, R.raw.stickers_guardian_taunt + "", getContext().getString(R.string.stickers_guardian_taunt));
        includeAsset(result, R.raw.stickers_ikora_book + "", getContext().getString(R.string.stickers_ikora_book));
        includeAsset(result, R.raw.stickers_ikora_taunt + "", getContext().getString(R.string.stickers_ikora_taunt));
        includeAsset(result, R.raw.stickers_petra + "", getContext().getString(R.string.stickers_petra));
        includeAsset(result, R.raw.stickers_rahool + "", getContext().getString(R.string.stickers_rahool));
        includeAsset(result, R.raw.stickers_saladin_no_no + "", getContext().getString(R.string.stickers_saladin_no_no));
        includeAsset(result, R.raw.stickers_shaxx_amazing + "", getContext().getString(R.string.stickers_shaxx_amazing));
        includeAsset(result, R.raw.stickers_shaxx_laugh + "", getContext().getString(R.string.stickers_shaxx_laugh));
        includeAsset(result, R.raw.stickers_sparrow + "", getContext().getString(R.string.stickers_sparrow));
        includeAsset(result, R.raw.stickers_treasure + "", getContext().getString(R.string.stickers_treasure));
        includeAsset(result, R.raw.stickers_uldren_nooo + "", getContext().getString(R.string.stickers_uldren_nooo));
        includeAsset(result, R.raw.stickers_uldren_taunt + "", getContext().getString(R.string.stickers_uldren_taunt));
        includeAsset(result, R.raw.stickers_variks_greetings + "", getContext().getString(R.string.stickers_variks_greetings));
        includeAsset(result, R.raw.stickers_xursday + "", getContext().getString(R.string.stickers_xursday));
        includeAsset(result, R.raw.stickers_zavala_facepalm + "", getContext().getString(R.string.stickers_zavala_facepalm));
        includeAsset(result, R.raw.stickers_zavala_scarf + "", getContext().getString(R.string.stickers_zavala_scarf));

        return result;
    }

    private void includeAsset(final MatrixCursor result, final String filename, final String title) {
        final MatrixCursor.RowBuilder row = result.newRow();
        // These columns are required
        row.add(Document.COLUMN_DOCUMENT_ID, filename);
        row.add(Document.COLUMN_DISPLAY_NAME, title);
        String mimeType = "image/png";
        row.add(Document.COLUMN_MIME_TYPE, mimeType);
        row.add(Document.COLUMN_FLAGS, Document.FLAG_SUPPORTS_THUMBNAIL);
        int size = 0;
        try {
          size = getContext().getResources().openRawResource(Integer.parseInt(filename)).available();
        } catch (Exception e) {
          e.printStackTrace();
        }
        row.add(Document.COLUMN_SIZE, size);
    }

    @Override
    public Cursor queryDocument(final String documentId, final String[] projection) throws FileNotFoundException {
        // Create a cursor with either the requested fields, or the default projection if "projection" is null.
        final MatrixCursor result = new MatrixCursor(projection != null ? projection : DEFAULT_DOCUMENT_PROJECTION);
        includeAsset(result, documentId, "DestinySticker.png");
        return result;
    }

    @Override
    public String getDocumentType(final String documentId) throws FileNotFoundException {
        // All stickers are PNG, so we don't actually have to get the type.
        return "image/png";
    }

    @Override
    public ParcelFileDescriptor openDocument(final String documentId, final String mode,
                                             final CancellationSignal signal) throws FileNotFoundException {
        // Unfortunately, the more obvious and straightforward approach of simply doing:
        // return getContext().getResources().openRawResourceFd(Integer.parseInt(documentId)).getParcelFileDescriptor();
        // does not work - the selected image will not actually show up in the app you are trying to use it in.
        // However, making a temporary copy and using that to generate the ParcelFileDescriptor solves the issue.

        String tempfile = getContext().getFilesDir() + "/DestinySticker.png";
        InputStream in = getContext().getResources().openRawResource(Integer.parseInt(documentId));
        FileOutputStream out = new FileOutputStream(tempfile);
        byte[] buff = new byte[1024];
        int read = 0;

        try {
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        File file = new File(tempfile);
        return ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
    }

    @Override
    public boolean onCreate() {
        return true;
    }
}
