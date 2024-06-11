/*
 *  Copyright 2021-2024 Disney Streaming
 *
 *  Licensed under the Tomorrow Open Source Technology License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     https://disneystreaming.github.io/TOST-1.0.txt
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package smithy4s.codegen

import software.amazon.smithy.model.loader.ModelDiscovery
import software.amazon.smithy.model.loader.ModelManifestException

import java.nio.file.FileSystems
import java.nio.file.Files
import scala.jdk.CollectionConverters._
import scala.util.Using

private[codegen] object LocalJarsFilter {

  def containsSmithyModel(localJars: List[os.Path]): List[os.Path] =
    localJars.filter { dep =>
      isAmazonSpecial(dep) || containsSmithyManifest(dep)
    }

  private def isAmazonSpecial(dep: os.Path) =
    dep.toString.contains("software/amazon/smithy")

  private def containsSmithyManifest(dep: os.Path) = {
    val file = dep.toIO
    Using.resource(FileSystems.newFileSystem(file.toPath(), null)) { jarFS =>
      val p = jarFS.getPath("META-INF", "smithy", "manifest")

      if (!Files.exists(p)) false
      else {
        try ModelDiscovery
          .findModels(p.toUri().toURL())
          .asScala
          .toList
          .nonEmpty
        catch {
          case e: ModelManifestException =>
            System.err.println(
              s"Unexpected exception while loading model from $file, skipping: $e"
            )
            false
        }
      }
    }
  }

}
