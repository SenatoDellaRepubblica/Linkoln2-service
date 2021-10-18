/* The following code was generated by JFlex 1.7.0 */

/*******************************************************************************
 * Copyright (c) 2016-2021 Institute of Legal Information and Judicial Systems IGSG-CNR (formerly ITTIG-CNR)
 * 
 * This program and the accompanying materials  are made available under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either version 3 of the License, or (at your option)
 * any later version. 
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at: https://www.gnu.org/licenses/gpl-3.0.txt
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is 
 * distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 *  
 * Authors: Lorenzo Bacci (IGSG-CNR)
 ******************************************************************************/
 package it.cnr.igsg.linkoln.service.impl.it;

import java.io.IOException;
import java.io.StringReader;

import it.cnr.igsg.linkoln.entity.*;
import it.cnr.igsg.linkoln.service.*;

import it.cnr.igsg.linkoln.service.impl.Util;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.7.0
 * from the specification file <tt>jflex/Journals.jflex</tt>
 */
public class Journals extends LinkolnAnnotationService {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;
  public static final int lkn = 2;
  public static final int officialJournalState = 4;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1,  1,  2, 2
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\13\1\2\1\0\1\13\1\1\22\0\1\13\5\0\1\5"+
    "\1\0\1\14\1\14\2\0\1\14\1\3\1\53\1\54\1\52\1\52"+
    "\1\52\1\52\1\52\1\52\1\52\1\52\1\52\1\52\1\15\1\12"+
    "\5\0\1\27\1\7\1\40\1\62\1\36\1\71\1\31\1\34\1\47"+
    "\1\34\1\65\1\60\1\56\1\6\1\51\1\11\1\34\1\33\1\24"+
    "\1\44\1\42\1\34\1\73\1\34\1\34\1\23\1\66\1\0\1\67"+
    "\1\0\1\4\1\0\1\26\1\20\1\37\1\61\1\35\1\70\1\30"+
    "\1\25\1\46\1\25\1\64\1\57\1\55\1\17\1\50\1\22\1\25"+
    "\1\32\1\21\1\43\1\41\1\25\1\72\1\25\1\25\1\16\12\0"+
    "\1\0\44\0\1\0\5\0\1\0\3\0\1\0\5\0\1\0\5\0"+
    "\1\25\1\25\4\25\1\0\1\25\1\25\1\25\2\25\1\25\1\25"+
    "\2\25\1\0\1\25\1\25\1\25\3\25\2\0\1\25\1\25\2\25"+
    "\3\0\1\25\1\25\4\25\1\0\1\25\1\25\1\25\2\25\1\25"+
    "\1\25\2\25\1\0\1\25\1\25\1\25\3\25\2\0\1\25\1\25"+
    "\2\25\53\0\1\25\1\25\6\0\2\45\66\0\1\25\1\25\4\0"+
    "\1\25\1\25\17\0\1\10\u1c88\0\1\25\1\25\216\0\2\25\42\0"+
    "\1\25\1\25\u0144\0\1\13\20\0\1\3\1\3\4\0\1\0\16\0"+
    "\1\0\1\0\u0100\0\1\63\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\udee5\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\3\0\4\1\2\2\2\3\35\0\1\4\73\0\1\5"+
    "\120\0\1\6\35\0\1\7\60\0\1\10";

  private static int [] zzUnpackAction() {
    int [] result = new int[261];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\74\0\170\0\264\0\360\0\u012c\0\u0168\0\u012c"+
    "\0\u01a4\0\u012c\0\u01e0\0\u021c\0\u0258\0\u0294\0\264\0\u02d0"+
    "\0\u030c\0\u0348\0\u0384\0\u03c0\0\u03fc\0\u0438\0\u0474\0\u04b0"+
    "\0\u04ec\0\u0528\0\u0564\0\u05a0\0\u05dc\0\u0618\0\u0654\0\u0690"+
    "\0\u06cc\0\u0708\0\u0744\0\u0780\0\u07bc\0\u07f8\0\u0834\0\u0870"+
    "\0\u012c\0\u08ac\0\u08e8\0\u0924\0\u0960\0\u099c\0\u09d8\0\u0a14"+
    "\0\u0a50\0\u0a8c\0\u0ac8\0\u0b04\0\u0b40\0\u0b7c\0\u0bb8\0\u0bf4"+
    "\0\u0c30\0\u0c6c\0\u0ca8\0\u0ce4\0\u0d20\0\u0d5c\0\u0d98\0\u0dd4"+
    "\0\u0e10\0\u0e4c\0\u0e88\0\u0ec4\0\u0f00\0\u0f3c\0\u0f78\0\u0fb4"+
    "\0\u0ff0\0\u102c\0\u1068\0\u10a4\0\u10e0\0\u111c\0\u1158\0\u1194"+
    "\0\u11d0\0\u120c\0\u1248\0\u1284\0\u12c0\0\u12fc\0\u1338\0\u1374"+
    "\0\u13b0\0\u13ec\0\u1428\0\u1464\0\u14a0\0\u14dc\0\u1518\0\u1554"+
    "\0\u1590\0\u15cc\0\u1608\0\u1644\0\u012c\0\u1680\0\u16bc\0\u16f8"+
    "\0\u1734\0\u1770\0\u17ac\0\u17e8\0\u1824\0\u1860\0\u189c\0\u18d8"+
    "\0\u1914\0\u1950\0\u198c\0\u19c8\0\u1a04\0\u1a40\0\u1a7c\0\u1ab8"+
    "\0\u1af4\0\u1b30\0\u1b6c\0\u1ba8\0\u1be4\0\u1c20\0\u1c5c\0\u1c98"+
    "\0\u1cd4\0\u1d10\0\u1d4c\0\u1d88\0\u1dc4\0\u1e00\0\u1e3c\0\u1e78"+
    "\0\u1eb4\0\u1ef0\0\u1f2c\0\u1f68\0\u1fa4\0\u1fe0\0\u201c\0\u2058"+
    "\0\u2094\0\u20d0\0\u210c\0\u2148\0\u2184\0\u21c0\0\u21fc\0\u2238"+
    "\0\u2274\0\u22b0\0\u22ec\0\u2328\0\u2364\0\u23a0\0\u23dc\0\u2418"+
    "\0\u2454\0\u2490\0\u24cc\0\u2508\0\u2544\0\u2580\0\u25bc\0\u25f8"+
    "\0\u2634\0\u2670\0\u26ac\0\u26e8\0\u2724\0\u2760\0\u279c\0\u27d8"+
    "\0\u2814\0\u2850\0\u288c\0\u28c8\0\u2904\0\u012c\0\u2940\0\u297c"+
    "\0\u29b8\0\u29f4\0\u2a30\0\u2a6c\0\u2aa8\0\u2ae4\0\u2b20\0\u2b5c"+
    "\0\u2b98\0\u2bd4\0\u2c10\0\u2c4c\0\u2c88\0\u2cc4\0\u2d00\0\u2d3c"+
    "\0\u2d78\0\u2db4\0\u2df0\0\u2e2c\0\u2e68\0\u2ea4\0\u2ee0\0\u2f1c"+
    "\0\u2f58\0\u2f94\0\u2fd0\0\u012c\0\u300c\0\u3048\0\u3084\0\u30c0"+
    "\0\u30fc\0\u3138\0\u3174\0\u31b0\0\u31ec\0\u3228\0\u3264\0\u32a0"+
    "\0\u32dc\0\u3318\0\u3354\0\u3390\0\u33cc\0\u3408\0\u3444\0\u3480"+
    "\0\u34bc\0\u34f8\0\u3534\0\u3570\0\u35ac\0\u35e8\0\u3624\0\u3660"+
    "\0\u369c\0\u36d8\0\u3714\0\u3750\0\u378c\0\u37c8\0\u3804\0\u3840"+
    "\0\u387c\0\u38b8\0\u38f4\0\u3930\0\u396c\0\u39a8\0\u39e4\0\u3a20"+
    "\0\u3a5c\0\u3a98\0\u3ad4\0\u3b10\0\u012c";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[261];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\4\1\5\4\4\2\6\1\4\1\6\4\4\27\6"+
    "\1\4\5\6\2\4\6\6\1\4\2\6\1\7\1\4"+
    "\4\6\66\10\1\11\5\10\66\12\1\13\5\12\11\0"+
    "\1\14\10\0\1\14\5\0\1\15\1\16\44\0\1\17"+
    "\6\0\1\14\10\0\1\14\5\0\1\15\1\16\147\0"+
    "\1\14\10\0\1\14\5\0\1\15\1\16\25\0\2\20"+
    "\67\0\1\21\76\0\2\22\54\0\2\23\57\0\2\24"+
    "\23\0\1\25\46\0\2\24\12\0\1\26\10\0\1\27"+
    "\103\0\3\30\65\0\2\31\76\0\3\32\15\0\1\33"+
    "\10\0\1\33\71\0\1\34\4\0\1\34\111\0\2\35"+
    "\32\0\4\36\1\37\4\0\4\36\35\0\1\36\12\0"+
    "\1\40\46\0\1\35\1\26\37\0\1\41\10\0\1\41"+
    "\137\0\3\42\14\0\1\43\10\0\1\43\63\0\1\44"+
    "\10\0\1\44\71\0\1\45\4\0\1\45\123\0\1\36"+
    "\21\0\4\36\1\37\4\0\4\36\50\0\1\40\13\0"+
    "\1\46\10\0\1\46\133\0\2\47\30\0\1\50\64\0"+
    "\1\51\10\0\1\51\71\0\1\52\135\0\2\53\50\0"+
    "\2\54\44\0\1\55\10\0\1\55\136\0\3\56\12\0"+
    "\1\57\1\0\2\57\1\0\1\57\11\0\2\57\2\0"+
    "\1\57\1\0\1\57\1\0\2\57\1\0\1\57\1\0"+
    "\1\57\1\0\1\57\1\0\1\57\2\0\1\57\1\0"+
    "\1\57\4\0\1\57\1\0\1\57\1\0\1\57\2\0"+
    "\1\57\3\0\1\57\1\0\1\57\4\0\1\60\1\0"+
    "\1\61\1\60\1\0\1\60\5\0\1\62\3\0\2\60"+
    "\2\0\1\60\1\0\1\60\1\0\2\60\1\0\1\60"+
    "\1\0\1\60\1\0\1\60\1\0\1\60\2\0\1\60"+
    "\1\0\1\60\4\0\1\60\1\0\1\60\1\63\1\64"+
    "\2\0\1\60\3\0\1\60\1\0\1\60\1\0\4\65"+
    "\1\66\2\0\1\67\1\0\4\65\3\0\1\67\2\0"+
    "\1\67\3\0\1\15\1\16\13\0\3\70\3\0\1\65"+
    "\12\0\1\71\50\0\2\72\37\0\1\73\10\0\1\73"+
    "\2\0\1\73\55\0\1\74\10\0\1\74\60\0\1\57"+
    "\1\0\2\57\1\0\1\57\3\0\1\75\5\0\2\57"+
    "\2\0\1\57\1\0\1\57\1\0\2\57\1\0\1\57"+
    "\1\0\1\57\1\0\1\57\1\0\1\57\2\0\1\57"+
    "\1\0\1\57\4\0\1\57\1\0\1\57\1\0\1\57"+
    "\2\0\1\57\3\0\1\57\1\0\1\57\4\0\1\60"+
    "\1\0\2\60\1\0\1\60\3\0\1\76\5\0\2\60"+
    "\2\0\1\60\1\0\1\60\1\0\2\60\1\0\1\60"+
    "\1\0\1\60\1\0\1\60\1\0\1\60\2\0\1\60"+
    "\1\0\1\60\4\0\1\60\1\0\1\60\1\0\1\60"+
    "\2\0\1\60\3\0\1\60\1\0\1\60\4\0\1\60"+
    "\1\0\2\60\1\0\1\60\3\0\1\76\5\0\2\60"+
    "\2\0\1\60\1\0\1\60\1\0\2\60\1\0\1\60"+
    "\1\0\1\60\1\77\1\100\1\0\1\60\2\0\1\60"+
    "\1\0\1\60\4\0\1\60\1\0\1\60\1\0\1\60"+
    "\2\0\1\60\3\0\1\60\1\0\1\60\41\0\2\77"+
    "\101\0\2\101\26\0\1\60\1\0\2\60\1\0\1\60"+
    "\3\0\1\76\5\0\2\60\2\0\1\60\1\0\1\60"+
    "\1\0\2\60\1\0\1\60\1\0\1\60\1\0\1\60"+
    "\1\0\1\60\2\0\1\60\1\101\1\102\4\0\1\60"+
    "\1\0\1\60\1\0\1\60\2\0\1\60\3\0\1\60"+
    "\1\0\1\60\1\0\4\65\1\66\2\0\1\67\1\0"+
    "\4\65\3\0\1\67\2\0\1\67\3\0\1\15\1\16"+
    "\34\0\1\71\13\0\1\103\10\0\1\103\115\0\2\104"+
    "\70\0\2\105\112\0\2\106\56\0\2\107\40\0\1\110"+
    "\10\0\1\110\66\0\1\111\61\0\2\75\1\0\2\75"+
    "\1\0\1\75\3\0\1\112\5\0\2\75\2\0\1\75"+
    "\1\0\1\75\1\0\2\75\1\0\1\75\1\0\1\75"+
    "\1\0\1\75\1\0\1\75\2\0\1\75\1\0\2\75"+
    "\3\0\1\75\1\0\1\75\1\0\1\75\2\0\1\75"+
    "\3\0\1\75\1\0\1\75\3\0\2\76\1\0\2\76"+
    "\1\0\1\76\3\0\1\113\5\0\2\76\2\0\1\76"+
    "\1\0\1\76\1\0\2\76\1\0\1\76\1\0\1\76"+
    "\1\0\1\76\1\0\1\76\2\0\1\76\1\0\2\76"+
    "\3\0\1\76\1\0\1\76\1\0\1\76\2\0\1\76"+
    "\3\0\1\76\1\0\1\76\55\0\2\114\21\0\1\60"+
    "\1\0\2\60\1\0\1\60\3\0\1\76\5\0\2\60"+
    "\2\0\1\60\1\0\1\60\1\0\2\60\1\0\1\60"+
    "\1\0\1\60\1\0\1\60\1\0\1\60\2\0\1\60"+
    "\1\0\1\60\3\0\1\114\1\115\1\0\1\60\1\0"+
    "\1\60\2\0\1\60\3\0\1\60\1\0\1\60\37\0"+
    "\2\116\37\0\1\60\1\0\2\60\1\0\1\60\3\0"+
    "\1\76\5\0\2\60\2\0\1\60\1\0\1\60\1\0"+
    "\2\60\1\0\1\60\1\116\1\117\1\0\1\60\1\0"+
    "\1\60\2\0\1\60\1\0\1\60\4\0\1\60\1\0"+
    "\1\60\1\0\1\60\2\0\1\60\3\0\1\60\1\0"+
    "\1\60\7\0\1\120\10\0\1\120\64\0\1\121\10\0"+
    "\1\121\77\0\2\122\127\0\3\123\34\0\2\124\56\0"+
    "\1\36\67\0\1\125\1\0\1\126\6\0\1\125\1\0"+
    "\1\126\2\0\1\126\121\0\1\127\73\0\1\130\30\0"+
    "\1\131\10\0\1\131\57\0\1\60\1\0\1\60\1\132"+
    "\1\0\1\60\3\0\1\76\2\0\1\131\2\0\2\60"+
    "\2\0\1\60\1\0\1\60\1\0\2\60\1\0\1\60"+
    "\1\0\1\60\1\0\1\60\1\0\1\60\2\0\1\60"+
    "\1\0\1\60\4\0\1\60\1\0\1\60\1\0\1\60"+
    "\2\0\1\60\3\0\1\60\1\0\1\60\4\0\1\133"+
    "\73\0\1\134\1\0\2\60\1\0\1\60\3\0\1\76"+
    "\5\0\2\60\2\0\1\60\1\0\1\60\1\0\2\60"+
    "\1\0\1\60\1\0\1\60\1\0\1\60\1\0\1\60"+
    "\2\0\1\60\1\0\1\60\4\0\1\60\1\0\1\60"+
    "\1\0\1\60\2\0\1\60\3\0\1\60\1\0\1\60"+
    "\10\0\1\135\10\0\1\135\2\0\1\135\60\0\1\136"+
    "\10\0\1\136\114\0\2\137\35\0\1\140\10\0\1\140"+
    "\55\0\2\124\2\0\1\141\5\0\1\124\25\0\2\142"+
    "\72\0\2\143\74\0\2\144\101\0\1\127\14\0\1\145"+
    "\56\0\1\130\14\0\1\146\41\0\2\147\41\0\1\60"+
    "\1\0\2\60\1\0\1\60\3\0\1\76\5\0\2\60"+
    "\2\0\1\60\1\0\1\60\1\0\2\60\1\147\1\150"+
    "\1\0\1\60\1\0\1\60\1\0\1\60\2\0\1\60"+
    "\1\0\1\60\4\0\1\60\1\0\1\60\1\0\1\60"+
    "\2\0\1\60\3\0\1\60\1\0\1\60\61\0\2\151"+
    "\15\0\1\60\1\0\2\60\1\0\1\60\3\0\1\76"+
    "\5\0\2\60\2\0\1\60\1\0\1\60\1\0\2\60"+
    "\1\0\1\60\1\0\1\60\1\0\1\60\1\0\1\60"+
    "\2\0\1\60\1\0\1\60\4\0\1\60\1\0\1\60"+
    "\1\151\1\152\2\0\1\60\3\0\1\60\1\0\1\60"+
    "\11\0\1\153\10\0\1\153\130\0\2\154\41\0\1\65"+
    "\6\0\1\65\10\0\1\65\1\0\1\65\40\0\1\155"+
    "\64\0\1\156\10\0\1\156\144\0\2\157\57\0\2\160"+
    "\26\0\1\161\10\0\1\161\51\0\66\162\1\0\5\162"+
    "\32\0\2\163\44\0\1\60\1\0\2\60\1\0\1\60"+
    "\3\0\1\76\5\0\2\60\2\0\1\60\1\0\1\60"+
    "\1\163\1\164\1\60\1\0\1\60\1\0\1\60\1\0"+
    "\1\60\1\0\1\60\2\0\1\60\1\0\1\60\4\0"+
    "\1\60\1\0\1\60\1\0\1\60\2\0\1\60\3\0"+
    "\1\60\1\0\1\60\26\0\2\165\50\0\1\60\1\0"+
    "\2\60\1\0\1\60\3\0\1\76\5\0\2\60\1\0"+
    "\1\165\1\166\1\0\1\60\1\0\2\60\1\0\1\60"+
    "\1\0\1\60\1\0\1\60\1\0\1\60\2\0\1\60"+
    "\1\0\1\60\4\0\1\60\1\0\1\60\1\0\1\60"+
    "\2\0\1\60\3\0\1\60\1\0\1\60\12\0\1\65"+
    "\62\0\2\167\2\0\1\170\5\0\1\167\21\0\2\171"+
    "\11\0\2\172\1\0\1\167\30\0\1\173\10\0\1\173"+
    "\2\0\1\173\56\0\1\174\10\0\1\174\143\0\2\175"+
    "\11\0\1\176\10\0\1\176\145\0\2\177\66\162\1\200"+
    "\5\162\15\0\1\201\62\0\1\60\1\0\2\60\1\0"+
    "\1\60\3\0\1\201\5\0\2\60\2\0\1\60\1\0"+
    "\1\60\1\0\2\60\1\0\1\60\1\0\1\60\1\0"+
    "\1\60\1\0\1\60\2\0\1\60\1\0\1\60\4\0"+
    "\1\60\1\0\1\60\1\0\1\60\2\0\1\60\3\0"+
    "\1\60\1\0\1\60\43\0\2\202\33\0\1\60\1\0"+
    "\2\60\1\0\1\60\3\0\1\76\5\0\2\60\2\0"+
    "\1\60\1\0\1\60\1\0\2\60\1\0\1\60\1\0"+
    "\1\60\1\0\1\60\1\202\1\203\2\0\1\60\1\0"+
    "\1\60\4\0\1\60\1\0\1\60\1\0\1\60\2\0"+
    "\1\60\3\0\1\60\1\0\1\60\1\0\2\167\2\0"+
    "\1\170\5\0\1\167\34\0\2\172\30\0\1\204\10\0"+
    "\1\204\131\0\2\205\47\0\2\206\103\0\2\207\37\0"+
    "\1\210\10\0\1\210\2\0\1\210\114\0\3\211\61\0"+
    "\2\212\52\0\1\213\132\0\1\214\22\0\2\201\1\0"+
    "\2\201\1\0\1\201\3\0\1\215\5\0\2\201\2\0"+
    "\1\201\1\0\1\201\1\0\2\201\1\0\1\201\1\0"+
    "\1\201\1\0\1\201\1\0\1\201\2\0\1\201\1\0"+
    "\2\201\3\0\1\201\1\0\1\201\1\0\1\201\2\0"+
    "\1\201\3\0\1\201\1\0\1\201\35\0\2\163\41\0"+
    "\1\60\1\0\2\60\1\0\1\60\3\0\1\76\5\0"+
    "\2\60\2\0\1\60\1\0\1\60\1\0\2\60\1\163"+
    "\1\164\1\0\1\60\1\0\1\60\1\0\1\60\2\0"+
    "\1\60\1\0\1\60\4\0\1\60\1\0\1\60\1\0"+
    "\1\60\2\0\1\60\3\0\1\60\1\0\1\60\7\0"+
    "\1\216\10\0\1\216\110\0\2\217\116\0\2\220\22\0"+
    "\1\221\10\0\1\221\62\0\1\222\10\0\1\222\110\0"+
    "\2\223\65\0\2\224\43\0\2\213\1\0\2\213\1\0"+
    "\1\213\3\0\1\225\5\0\2\213\2\0\1\213\1\0"+
    "\1\213\1\0\2\213\1\0\1\213\1\0\1\213\1\0"+
    "\1\213\1\0\1\213\2\0\1\213\1\0\2\213\3\0"+
    "\1\213\1\0\1\213\1\0\1\213\2\0\1\213\3\0"+
    "\1\213\1\0\1\213\57\0\2\226\65\0\1\227\31\0"+
    "\1\230\10\0\1\230\2\0\1\230\55\0\1\231\10\0"+
    "\1\231\55\0\4\232\1\233\4\0\4\232\12\0\1\15"+
    "\1\16\13\0\3\234\3\0\1\232\12\0\1\235\77\0"+
    "\2\236\12\0\1\124\126\0\3\237\41\0\1\240\130\0"+
    "\1\241\104\0\3\242\60\0\1\227\14\0\1\243\15\0"+
    "\1\244\10\0\1\244\114\0\2\245\30\0\4\232\1\233"+
    "\4\0\4\232\12\0\1\15\1\16\34\0\1\235\13\0"+
    "\1\246\10\0\1\246\62\0\1\247\10\0\1\247\133\0"+
    "\2\250\30\0\1\251\104\0\2\252\47\0\2\240\1\0"+
    "\2\240\1\0\1\240\3\0\1\253\5\0\2\240\2\0"+
    "\1\240\1\0\1\240\1\0\2\240\1\0\1\240\1\0"+
    "\1\240\1\0\1\240\1\0\1\240\2\0\1\240\1\0"+
    "\2\240\3\0\1\240\1\0\1\240\1\0\1\240\2\0"+
    "\1\240\3\0\1\240\1\0\1\240\52\0\1\241\14\0"+
    "\1\254\12\0\1\255\10\0\1\255\54\0\66\256\1\0"+
    "\5\256\12\0\1\167\131\0\2\167\31\0\1\257\10\0"+
    "\1\257\54\0\4\232\1\233\4\0\4\232\10\0\2\260"+
    "\1\15\1\16\21\0\1\232\12\0\1\235\70\0\3\261"+
    "\11\0\2\251\1\0\2\251\1\0\1\251\3\0\1\262"+
    "\5\0\2\251\2\0\1\251\1\0\1\251\1\0\2\251"+
    "\1\0\1\251\1\0\1\251\1\0\1\251\1\0\1\251"+
    "\2\0\1\251\1\0\2\251\3\0\1\251\1\0\1\251"+
    "\1\0\1\251\2\0\1\251\3\0\1\251\1\0\1\251"+
    "\57\0\2\263\65\0\1\264\21\0\66\265\1\0\5\265"+
    "\67\0\1\266\4\0\66\256\1\267\5\256\10\0\1\270"+
    "\10\0\1\270\2\0\1\270\101\0\2\271\46\0\1\272"+
    "\10\0\1\272\126\0\1\273\56\0\2\36\107\0\1\264"+
    "\14\0\1\274\4\0\66\265\1\275\5\265\54\0\1\276"+
    "\30\0\1\277\10\0\1\277\116\0\3\300\41\0\1\301"+
    "\130\0\1\273\14\0\1\302\4\0\66\303\1\0\5\303"+
    "\54\0\1\304\76\0\2\305\25\0\1\232\131\0\2\232"+
    "\32\0\1\306\10\0\1\306\2\0\1\306\47\0\66\307"+
    "\1\0\5\307\66\303\1\310\5\303\57\0\2\311\76\0"+
    "\3\312\51\0\2\313\27\0\66\307\1\314\5\307\54\0"+
    "\1\315\102\0\3\316\14\0\1\317\10\0\1\317\65\0"+
    "\1\320\10\0\1\320\125\0\1\321\76\0\2\322\21\0"+
    "\1\323\10\0\1\323\143\0\1\324\76\0\2\325\57\0"+
    "\2\326\76\0\3\327\75\0\1\36\21\0\1\330\141\0"+
    "\3\331\14\0\1\332\10\0\1\332\57\0\2\330\1\0"+
    "\2\330\1\0\1\330\3\0\1\333\5\0\2\330\2\0"+
    "\1\330\1\0\1\330\1\0\2\330\1\0\1\330\1\0"+
    "\1\330\1\0\1\330\1\0\1\330\2\0\1\330\1\0"+
    "\2\330\3\0\1\330\1\0\1\330\1\0\1\330\2\0"+
    "\1\330\3\0\1\330\1\0\1\330\6\0\1\334\10\0"+
    "\1\334\143\0\1\335\56\0\1\336\110\0\1\65\5\0"+
    "\4\335\1\337\4\0\4\335\50\0\1\340\57\0\1\336"+
    "\14\0\1\341\12\0\1\342\10\0\1\342\133\0\2\343"+
    "\13\0\66\344\1\0\5\344\7\0\1\345\10\0\1\345"+
    "\136\0\3\346\6\0\66\344\1\347\5\344\10\0\1\350"+
    "\10\0\1\350\2\0\1\350\55\0\1\351\10\0\1\351"+
    "\130\0\1\352\30\0\1\353\10\0\1\353\66\0\1\354"+
    "\135\0\2\355\25\0\1\335\71\0\1\356\10\0\1\356"+
    "\2\0\1\356\34\0\2\357\74\0\3\360\51\0\2\361"+
    "\77\0\2\362\30\0\1\363\10\0\1\363\65\0\1\364"+
    "\10\0\1\364\110\0\2\365\122\0\1\232\76\0\2\224"+
    "\4\0\1\366\150\0\2\367\37\0\2\370\107\0\2\371"+
    "\64\0\2\372\52\0\1\373\61\0\2\373\1\0\2\373"+
    "\1\0\1\373\3\0\1\374\5\0\2\373\2\0\1\373"+
    "\1\0\1\373\1\0\2\373\1\0\1\373\1\0\1\373"+
    "\1\0\1\373\1\0\1\373\2\0\1\373\1\0\2\373"+
    "\3\0\1\373\1\0\1\373\1\0\1\373\2\0\1\373"+
    "\3\0\1\373\1\0\1\373\52\0\1\375\73\0\1\375"+
    "\14\0\1\376\4\0\66\377\1\0\73\377\1\u0100\5\377"+
    "\54\0\1\u0101\76\0\2\u0102\76\0\3\u0103\14\0\1\u0104"+
    "\10\0\1\u0104\143\0\1\u0105\4\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[15180];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\3\0\2\1\1\11\1\1\1\11\1\1\1\11\1\1"+
    "\35\0\1\11\73\0\1\11\120\0\1\11\35\0\1\11"+
    "\60\0\1\11";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[261];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true iff the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true iff the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;
  
  /** 
   * The number of occupied positions in zzBuffer beyond zzEndRead.
   * When a lead/high surrogate has been read from the input stream
   * into the final zzBuffer position, this will have a value of 1;
   * otherwise, it will have a value of 0.
   */
  private int zzFinalHighSurrogate = 0;

  /* user code: */
	
	/* Custom java code */

    @Override
	public String language() { return "IT"; }

	@Override
	public String version() { return "0.2"; }


	/* An empty default constructor is required to comply with LinkolnService */
	
	public Journals() { }
	
	@Override
	public final boolean run() {
		
		try {
			
			yyreset(new StringReader(getInput()));
			yylex();
			
		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	@Override
	protected void addValue() {
	
		//annotationEntity.setValue("value");
	} 



  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public Journals(java.io.Reader in) {
    this.zzReader = in;
  }


  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x110000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 382) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length - zzFinalHighSurrogate) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzBuffer.length*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
    }

    /* fill the buffer with new input */
    int requested = zzBuffer.length - zzEndRead;
    int numRead = zzReader.read(zzBuffer, zzEndRead, requested);

    /* not supposed to occur according to specification of java.io.Reader */
    if (numRead == 0) {
      throw new java.io.IOException("Reader returned 0 characters. See JFlex examples for workaround.");
    }
    if (numRead > 0) {
      zzEndRead += numRead;
      /* If numRead == requested, we might have requested to few chars to
         encode a full Unicode character. We assume that a Reader would
         otherwise never return half characters. */
      if (numRead == requested) {
        if (Character.isHighSurrogate(zzBuffer[zzEndRead - 1])) {
          --zzEndRead;
          zzFinalHighSurrogate = 1;
        }
      }
      /* potentially more input available */
      return false;
    }

    /* numRead < 0 ==> end of stream */
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * Internal scan buffer is resized down to its initial length, if it has grown.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    zzFinalHighSurrogate = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
    if (zzBuffer.length > ZZ_BUFFERSIZE)
      zzBuffer = new char[ZZ_BUFFERSIZE];
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public int yylex() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      yychar+= zzMarkedPosL-zzStartRead;

      boolean zzR = false;
      int zzCh;
      int zzCharCount;
      for (zzCurrentPosL = zzStartRead  ;
           zzCurrentPosL < zzMarkedPosL ;
           zzCurrentPosL += zzCharCount ) {
        zzCh = Character.codePointAt(zzBufferL, zzCurrentPosL, zzMarkedPosL);
        zzCharCount = Character.charCount(zzCh);
        switch (zzCh) {
        case '\u000B':  // fall through
        case '\u000C':  // fall through
        case '\u0085':  // fall through
        case '\u2028':  // fall through
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn += zzCharCount;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
        return YYEOF;
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1: 
            { addText(yytext());
	position++;
            } 
            // fall through
          case 9: break;
          case 2: 
            { addText(yytext());
		position++;
            } 
            // fall through
          case 10: break;
          case 3: 
            { checkEnd();
            } 
            // fall through
          case 11: break;
          case 4: 
            { addText(yytext());
		position--; //account for the following {LKN_C} character
		yybegin(YYINITIAL);
            } 
            // fall through
          case 12: break;
          case 5: 
            { addText(yytext()); 
	yybegin(lkn);
            } 
            // fall through
          case 13: break;
          case 6: 
            { offset += yylength();
		
		AnnotationEntity retrievedEntity = retrieveEntity(yytext());
		
		position += retrievedEntity.getText().length();
            } 
            // fall through
          case 14: break;
          case 7: 
            { offset += yylength();
		
		AnnotationEntity retrievedEntity = retrieveEntity(yytext());
		
		annotationEntity.addRelatedEntity(retrievedEntity);
		retrievedEntity.addRelatedEntity(annotationEntity);
		
		position += retrievedEntity.getText().length();
            } 
            // fall through
          case 15: break;
          case 8: 
            { start(new OfficialJournal(), officialJournalState, true, false);
            } 
            // fall through
          case 16: break;
          default:
            zzScanError(ZZ_NO_MATCH);
        }
      }
    }
  }

  /**
   * Runs the scanner on input files.
   *
   * This is a standalone scanner, it will print any unmatched
   * text to System.out unchanged.
   *
   * @param argv   the command line, contains the filenames to run
   *               the scanner on.
   */
  public static void main(String argv[]) {
    if (argv.length == 0) {
      System.out.println("Usage : java Journals [ --encoding <name> ] <inputfile(s)>");
    }
    else {
      int firstFilePos = 0;
      String encodingName = "UTF-8";
      if (argv[0].equals("--encoding")) {
        firstFilePos = 2;
        encodingName = argv[1];
        try {
          java.nio.charset.Charset.forName(encodingName); // Side-effect: is encodingName valid? 
        } catch (Exception e) {
          System.out.println("Invalid encoding '" + encodingName + "'");
          return;
        }
      }
      for (int i = firstFilePos; i < argv.length; i++) {
        Journals scanner = null;
        try {
          java.io.FileInputStream stream = new java.io.FileInputStream(argv[i]);
          java.io.Reader reader = new java.io.InputStreamReader(stream, encodingName);
          scanner = new Journals(reader);
          while ( !scanner.zzAtEOF ) scanner.yylex();
        }
        catch (java.io.FileNotFoundException e) {
          System.out.println("File not found : \""+argv[i]+"\"");
        }
        catch (java.io.IOException e) {
          System.out.println("IO error scanning file \""+argv[i]+"\"");
          System.out.println(e);
        }
        catch (Exception e) {
          System.out.println("Unexpected exception:");
          e.printStackTrace();
        }
      }
    }
  }


}
